# Variáveis
RESOURCE_GROUP="rg-global-solution-2025"
LOCATION="eastus"
ACR_NAME="acrglobalsolutionrm556715"
ACI_NAME="aci-global-solution"

# 1. Criar Grupo de Recursos
echo "Criando Resource Group: $RESOURCE_GROUP..."
az group create --name $RESOURCE_GROUP --location $LOCATION

# 2. Criar Azure Container Registry (ACR)
echo "Criando ACR: $ACR_NAME..."
az acr create --resource-group $RESOURCE_GROUP --name $ACR_NAME --sku Basic --admin-enabled true

echo "Infraestrutura de Registry criada."
echo "O Azure Container Instance (ACI) será provisionado pela Pipeline."

# 4. Exibir credenciais
echo "------------------------------------------------"
echo "infraestrutura:"
echo "Resource Group: $RESOURCE_GROUP"
echo "ACR Login Server: $(az acr show --name $ACR_NAME --query loginServer --output tsv)"
echo "ACR Username: $(az acr credential show --name $ACR_NAME --query username --output tsv)"
echo "ACR Password: $(az acr credential show --name $ACR_NAME --query passwords[0].value --output tsv)"
echo "------------------------------------------------"