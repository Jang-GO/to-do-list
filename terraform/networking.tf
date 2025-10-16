# 가상 네트워크 (VCN)
resource "oci_core_vcn" "todo_vcn" {
  compartment_id = var.compartment_ocid
  display_name   = "todo-app-vcn-simple"
  cidr_block     = "10.0.0.0/16"
}

# 인터넷 게이트웨이
resource "oci_core_internet_gateway" "todo_igw" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.todo_vcn.id
  display_name   = "todo-app-igw-simple"
}

# 경로 테이블 (Route Table)
resource "oci_core_route_table" "public_rt" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.todo_vcn.id
  display_name   = "todo-app-rt-simple"
  route_rules {
    destination       = "0.0.0.0/0"
    network_entity_id = oci_core_internet_gateway.todo_igw.id
  }
}

# 보안 목록 (Security List)
resource "oci_core_security_list" "default_sl" {
  compartment_id = var.compartment_ocid
  vcn_id         = oci_core_vcn.todo_vcn.id
  display_name   = "todo-app-sl-simple"

  # 외부에서 8080 포트(Spring Boot 앱)로 들어오는 요청 허용
  ingress_security_rules {
    protocol  = "6" # TCP
    source    = "0.0.0.0/0"
    tcp_options {
      min = 8080
      max = 8080
    }
  }
  # 외부에서 22 포트(SSH)로 들어오는 요청 허용
  ingress_security_rules {
    protocol  = "6" # TCP
    source    = "0.0.0.0/0"
    tcp_options {
      min = 22
      max = 22
    }
  }

  # 모든 나가는 트래픽 허용
  egress_security_rules {
    protocol    = "all"
    destination = "0.0.0.0/0"
  }
}

# 공용 서브넷 (Public Subnet)
resource "oci_core_subnet" "public_subnet" {
  compartment_id    = var.compartment_ocid
  vcn_id            = oci_core_vcn.todo_vcn.id
  display_name      = "todo-app-subnet-simple"
  cidr_block        = "10.0.1.0/24"
  route_table_id    = oci_core_route_table.public_rt.id
  security_list_ids = [oci_core_security_list.default_sl.id]
}