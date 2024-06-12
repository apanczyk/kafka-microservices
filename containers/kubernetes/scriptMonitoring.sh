kubectl create namespace monitoring 
cd prometheus
kubectl apply -f . --namespace=monitoring 
cd ../grafana 
kubectl apply -f . --namespace=monitoring


echo "https://devopscube.com/setup-prometheus-monitoring-on-kubernetes/"
echo "https://devopscube.com/setup-grafana-kubernetes/"
