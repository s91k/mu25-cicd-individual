# MU25 Countinous integration och test - Individuell inlämning

## Om applikationen

Det är repo:t innehåller en webbapplikation för en bokhandel. Frontend i React visar böcker, låter användaren lägga böcker i en varukorg och genomföra ett köp. Ett Spring Boot-API hanterar böcker och beställningar. Backend använder en H2-databas.

## Kör lokalt

Förutsättningar: Java 25 och Node.js med npm.

1. Starta backend i ett terminalfönster:

   ```powershell
   cd backend
   .\mvnw.cmd spring-boot:run
   ```

2. Starta frontend i ett annat terminalfönster:

   ```powershell
   cd frontend
   npm install
   npm run dev
   ```

3. Öppna adressen som Vite skriver ut, normalt `http://localhost:5173`.

## CI/CD

GitHub Actions kör automatiska tester och driftsättningar:

- En pull request till `dev` som ändrar filer under `backend/` kör backendens Maven-tester (`mvn clean test`).
- En pull request till `main` kör end-to-end-tester: bygger backend, startar backend och frontend, installerar Playwright och kör testerna.
- En push till `dev` som ändrar `backend/` bygger en Docker-image, publicerar den till Docker Hub med taggen `dev` och triggar driftsättning av backend på Render.
- En push till `main` som ändrar `backend/` gör motsvarande med taggen `prod` och produktionsmiljön på Render.
- En push till `dev` eller `main` som ändrar `frontend/` triggar driftsättning av frontend till motsvarande Render-miljö.

GitHub Secrets används för Docker Hub-uppgifter och Render deploy hooks.

## Live-applikation

### Produktion

Frontend: [https://bookstore-frontend-prod.onrender.com](https://bookstore-frontend-prod.onrender.com)

Backend: [https://bookstore-backend-prod.onrender.com](https://bookstore-backend-prod.onrender.com)

### Development

Frontend: [https://mu25-cicd-individual.onrender.com](https://mu25-cicd-individual.onrender.com)

Backend: [https://bookstore-backend-dev-65tv.onrender.com](https://bookstore-backend-dev-65tv.onrender.com)
