# 'Always Free' MySQL 데이터베이스 생성
resource "oci_mysql_mysql_db_system" "todo_db" {
  compartment_id      = var.compartment_ocid
  shape_name          = "MySQL.Free"
  display_name        = "todo-app-db-simple"
  subnet_id           = oci_core_subnet.public_subnet.id
  is_highly_available = false
  admin_username      = "admin"
  admin_password      = var.db_admin_password
  availability_domain = data.oci_identity_availability_domains.ad.availability_domains[0].name
}

# 현재 리전에서 사용할 최신 Oracle Linux Cloud Developer 이미지 정보를 가져옴
# AMD(x86) shape과 호환되는 이미지를 필터링
data "oci_core_images" "latest_oracle_linux_x86" {
  compartment_id           = var.tenancy_ocid
  operating_system         = "Oracle Linux Cloud Developer"
  operating_system_version = "8"
  shape                    = "VM.Standard.E2.1.Micro"
  sort_by                  = "TIMECREATED"
  sort_order               = "DESC"
}

# 'Always Free' VM(앱 서버) 생성
resource "oci_core_instance" "todo_app_vm" {
  compartment_id      = var.compartment_ocid
  shape               = "VM.Standard.E2.1.Micro"
  display_name        = "todo-app-vm-simple"
  availability_domain = data.oci_identity_availability_domains.ad.availability_domains[0].name

  create_vnic_details {
    subnet_id        = oci_core_subnet.public_subnet.id
    assign_public_ip = true
  }

  source_details {
    source_type = "image"
    source_id   = data.oci_core_images.latest_oracle_linux_x86.images[0].id
  }

  metadata = {
    ssh_authorized_keys = var.ssh_public_key
    user_data = base64encode(templatefile(
      "${path.module}/cloud-init.yaml", {
        db_endpoint = oci_mysql_mysql_db_system.todo_db.ip_address,
        db_user     = oci_mysql_mysql_db_system.todo_db.admin_username,
        db_password = var.db_admin_password
      }
    ))
  }
}

# 현재 리전의 가용 도메인 정보를 가져옴
data "oci_identity_availability_domains" "ad" {
  compartment_id = var.compartment_ocid
}