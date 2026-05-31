
3. Invoice Microservice
Descripción

El microservicio de facturas se encarga de generar y administrar facturas dentro del sistema.
Su responsabilidad principal es registrar la información de una factura, incluyendo datos del emisor, receptor, folio, fecha y montos.

Funcionalidades principales
Crear facturas.
Generar folio automático.
Consultar facturas.
Buscar factura por folio.
Contar facturas registradas.
Anular facturas.
Manejar datos del emisor y receptor.
Registrar montos netos, IVA y total.
Proteger endpoints mediante JWT.
Endpoints principales
POST /api/v1/invoices

Crea una nueva factura.

Ejemplo de body:

{
  "fecha": "2026-05-31",
  "razonSocialReceptor": "Cliente Ejemplo SPA",
  "giroReceptor": "Comercio",
  "direccionReceptor": "Av. Siempre Viva 123",
  "rutReceptor": "11111111-1",
  "razonSocialEmisor": "Empresa Emisora SPA",
  "giroEmisor": "Venta de productos",
  "direccionEmisor": "Av. Principal 456",
  "rutEmisor": "22222222-2"
}
GET /api/v1/invoices

Lista todas las facturas.

GET /api/v1/invoices/{folio}

Busca una factura por folio.

GET /api/v1/invoices/count

Obtiene la cantidad total de facturas.

PUT /api/v1/invoices/{folio}/anular

Anula una factura existente.

Autenticación

Los microservicios utilizan autenticación mediante JWT.
Para consumir endpoints protegidos se debe enviar el token en el header:

Authorization: Bearer TU_TOKEN

Ejemplo en Postman:

Key	Value
Authorization	Bearer eyJhbGciOiJIUzI1NiJ9...

El token se obtiene desde el microservicio de autenticación mediante login.

Ejemplo:

POST /api/v1/auth/login

Body:

{
  "usuario": "test",
  "password": "password"
}