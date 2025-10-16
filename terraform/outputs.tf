output "app_vm_public_ip" {
  description = "배포된 애플리케이션 VM의 공인 IP 주소"
  value       = oci_core_instance.todo_app_vm.public_ip
}