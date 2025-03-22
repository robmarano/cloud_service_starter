minikube config set cpus 8
minikube config set memory 12288
minikube start --driver=docker --addons=dashboard --addons=metrics-server --addons=ingress --addons=ingress-dns
