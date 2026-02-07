📧 Smart Email Assistant

Smart Email Assistant is a full-stack web application that generates professional email replies using AI.
Users can paste an email, select a tone, and instantly receive a well-structured reply powered by Google’s Gemini API.

🚀 Project Overview

Writing professional emails can be time-consuming and challenging.
Smart Email Assistant simplifies this by leveraging AI to generate context-aware email replies based on user input and preferred tone.

Key Features

✍️ Generate email replies from raw email content

🎯 Select tone (Professional, Casual, Friendly)

⚡ Fast AI-powered responses

🌐 Deployed backend and frontend

🔒 Secure API key handling via environment variables

---------------------------------------------------------------------------------------
🛠 Tech Stack Used
Frontend
-React.js
-Vercel (Deployment)

Backend
-Java 17
-Spring Boot
-Spring Web
-Spring WebFlux (WebClient)
-Maven
-Railway (Deployment)

AI Integration
-Google Gemini API (gemini-2.0-flash) 

------------------------------------------------------------------------------------------

▶️ Steps to Run the Project Locally

1️⃣ Clone the Repository
git clone https://github.com/Sujanian3442/Smart-Email.git
cd Smart-Email

2️⃣ Backend Setup (Spring Boot)
Java 17+
Maven
Gemini API Key
Configure application.properties
spring.application.name=email-writer
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent
gemini.api.key=YOUR_GEMINI_API_KEY
Run Backend
cd email-writer
mvn spring-boot:run
Backend will start at:
http://localhost:8080


Test:
GET http://localhost:8080/

3️⃣ Frontend Setup (React)
Node.js 18+
npm or yarn
Install Dependencies
cd email-writer-react
npm install

Start Frontend
npm start


Frontend will run at:
http://localhost:3000

🔌 API Endpoints
Health Check
GET /

Response:
Smart Email Backend is running 🚀

Generate Email Reply
POST /generate
Request Body

{
  "emailContent": "How are you?",
  "tone": "professional"
}

Response

I hope this email finds you well. Thank you for reaching out...
