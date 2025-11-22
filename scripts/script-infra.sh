# Variáveis
RESOURCE_GROUP="rg-global-solution-2025"
LOCATION="eastus"
ACR_NAME="acrglobalsolutionrm556715"
ACI_NAME="aci-global-solution"

# criar Grupo de Recursos
echo "Criando Resource Group: $RESOURCE_GROUP..."
az group create --name $RESOURCE_GROUP --location $LOCATION

# criar Azure Container Registry (ACR)
echo "Criando ACR: $ACR_NAME..."
az acr create --resource-group $RESOURCE_GROUP --name $ACR_NAME --sku Basic --admin-enabled true

echo "Importando imagens oficiais para o ACR"
az acr import --name $ACR_NAME --source docker.io/library/postgres:15-alpine --image postgres:15-alpine --force
az acr import --name $ACR_NAME --source docker.io/library/redis:6.2-alpine --image redis:6.2-alpine --force
az acr import --name $ACR_NAME --source docker.io/library/rabbitmq:3-management --image rabbitmq:3-management --force


# exibir credenciais
echo "------------------------------------------------"
echo "infraestrutura:"
echo "Resource Group: $RESOURCE_GROUP"
echo "ACR Login Server: $(az acr show --name $ACR_NAME --query loginServer --output tsv)"
echo "ACR Username: $(az acr credential show --name $ACR_NAME --query username --output tsv)"
echo "ACR Password: $(az acr credential show --name $ACR_NAME --query passwords[0].value --output tsv)"
echo "------------------------------------------------"