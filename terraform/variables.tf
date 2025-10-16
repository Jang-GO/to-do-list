variable "tenancy_ocid" {
  type        = string
  description = "OCI 테넌시의 OCID. 플랫폼 이미지를 검색하는 데 사용됩니다."
}

variable "compartment_ocid" {
  type        = string
  description = "인프라를 생성할 OCI Compartment의 OCID"
}

variable "ssh_public_key" {
  type        = string
  description = "VM에 접속하기 위한 SSH 공개키"
  sensitive   = true
}

variable "db_admin_password" {
  type        = string
  description = "생성할 MySQL 데이터베이스의 admin 계정 비밀번호"
  sensitive   = true
}
