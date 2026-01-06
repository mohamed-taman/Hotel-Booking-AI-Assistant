# Hotel Booking Customer Support - AI-Powered Chat Assistant

An intelligent customer support application that helps users manage their hotel bookings through a conversational AI interface. Built with Spring AI, this application demonstrates the power of AI-driven customer service, enabling customers to view, modify, and cancel their hotel reservations through natural language conversations.

## Table of Contents
- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [Main Features](#main-features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Clone the Repository](#clone-the-repository)
  - [Configuration](#configuration)
  - [Build the Project](#build-the-project)
  - [Run the Application](#run-the-application)
  - [Run Tests](#run-tests)
- [Using the Application](#using-the-application)
- [Docker Support](#docker-support)
- [What's Next](#whats-next)
- [License](#license)
- [Author](#author)

## Project Overview

This application showcases an AI-powered customer support system for hotel booking management. It leverages Spring AI with OpenAI's GPT models to create an intelligent chatbot that can:
- Retrieve booking information securely after validating customer credentials
- Modify room types based on availability and terms of service
- Cancel bookings according to the hotel's cancellation policy
- Answer questions about booking terms and policies using RAG (Retrieval-Augmented Generation)

The system uses a modern tech stack combining Spring Boot for the backend, Vaadin for the UI, and Spring AI for intelligent conversation handling.

## Project Structure

```
Hotel-Booking-Customer-Support/
├── .mvn/                              # Maven wrapper configuration
├── src/
│   ├── main/
│   │   ├── frontend/                  # React/TypeScript frontend components
│   │   │   ├── components/            # Reusable UI components (Message, MessageList)
│   │   │   ├── views/                 # Main application views
│   │   │   └── generated/             # Vaadin auto-generated React integration code
│   │   ├── java/
│   │   │   └── rs/siriusxi/hbca/     # Main application package
│   │   │       ├── HCSAApplication.java      # Spring Boot entry point & vector store initialization
│   │   │       ├── config/            # Spring configuration classes
│   │   │       │   ├── AppConfig.java        # Application-wide configuration
│   │   │       │   └── FunctionConfig.java   # AI function calling configuration
│   │   │       ├── domain/            # Domain entities
│   │   │       │   ├── Booking.java          # Hotel booking entity
│   │   │       │   ├── BookingStatus.java    # Booking status enum
│   │   │       │   ├── Customer.java         # Customer entity
│   │   │       │   └── RoomType.java         # Room type enum
│   │   │       ├── repository/        # Data access layer
│   │   │       │   ├── BookingRepository.java   # Spring Data JPA Booking repository
│   │   │       │   └── CustomerRepository.java  # Spring Data JPA Customer repository
│   │   │       ├── service/           # Business logic layer
│   │   │       │   ├── HotelBookingService.java     # Booking management operations
│   │   │       │   ├── HotelBookingDetails.java     # Booking DTO
│   │   │       │   └── ai/
│   │   │       │       └── CustomerSupportAssistant.java  # AI chat client with advisors
│   │   │       └── ui/                # UI service endpoints
│   │   │           ├── AssistantUIService.java      # Chat endpoint for frontend
│   │   │           └── HotelBookingUIService.java   # Booking data endpoint
│   │   └── resources/
│   │       └── booking-terms.txt      # Hotel terms & conditions for RAG
│   └── test/                          # Test resources and classes
├── target/                            # Compiled classes and build artifacts
├── node_modules/                      # NPM dependencies
├── pom.xml                            # Maven project configuration
├── package.json                       # Node.js dependencies for frontend
├── Dockerfile                         # Docker image configuration
├── tsconfig.json                      # TypeScript configuration
├── vite.config.ts                     # Vite build tool configuration
├── AGENTS.md                          # AI development guidance
├── LICENSE                            # MIT License
└── README.md                          # This file
```

## Main Features

### 1. **AI-Powered Conversational Interface**
- Natural language processing for customer queries
- Context-aware responses using chat memory
- Friendly and helpful tone matching hotel customer service standards

### 2. **Secure Booking Management**
- Validates customer identity before showing booking details
- Requires booking number, first name, and last name verification
- Prevents unauthorized access to sensitive booking information

### 3. **Smart Function Calling**
- Automatic function execution based on customer intent
- Parallel function calling support for efficiency
- Tools for booking cancellation and room type modifications

### 4. **RAG-Based Policy Enforcement**
- Vector store integration for booking terms and conditions
- Intelligent policy lookup before allowing booking changes
- Ensures compliance with hotel policies automatically

### 5. **Real-time Chat Interface**
- Reactive streaming responses for better user experience
- Markdown support for formatted responses
- Message history preservation across the conversation

### 6. **Modern Full-Stack Architecture**
- React-based frontend with TypeScript
- Spring Boot backend with reactive programming
- Vaadin Hilla for seamless frontend-backend integration

## Technology Stack

### Backend
- **Java 25** - Latest Java LTS with preview features enabled
- **Spring Boot 4.0.1** - Application framework
- **Spring AI 2.0.0-M1** - AI integration framework
  - OpenAI integration for chat completions
  - Vector store support for RAG
  - Chat memory for conversation context
  - Function calling for tool use
- **Spring Data JPA** - Data persistence
- **H2 Database** - Local file-based database for persistence
- **Flyway** - Database migration tool
- **Lombok** - Reduces boilerplate code

### Frontend
- **React 19.2.3** - UI library
- **TypeScript 5.9.3** - Type-safe JavaScript
- **Vaadin 25.0.2** - Full-stack framework
  - Hilla for React integration
  - React Components & Components Pro
- **Vite 7.3.0** - Fast build tool
- **react-markdown** - Markdown rendering in chat

### Build & Development Tools
- **Maven 3.x** - Build automation
- **Docker** - Containerization
- **Vaadin Maven Plugin** - Frontend build integration

## Getting Started

### Prerequisites

Before running this application, ensure you have:

1. **Java Development Kit (JDK) 25** or higher
   ```bash
   java -version
   ```

2. **Maven 3.6+** (or use the included Maven wrapper)
   ```bash
   mvn -version
   ```

3. **Node.js 18+** and **npm** (for frontend build)
   ```bash
   node --version
   npm --version
   ```

4. **OpenAI API Key** - Required for AI functionality
   - Sign up at [OpenAI](https://platform.openai.com/)
   - Generate an API key from your account dashboard

### Clone the Repository

```bash
git clone https://github.com/mohamed-taman/Hotel-Booking-Customer-Support.git
cd Hotel-Booking-Customer-Support
```

### Configuration

Create an `application.properties` or `application.yml` file in `src/main/resources/` with your OpenAI API key:

**Option 1: application.properties**
```properties
# OpenAI Configuration
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4
spring.ai.openai.chat.options.temperature=0.7

# Server Configuration (optional)
server.port=8080

# Logging (optional)
logging.level.rs.siriusxi.hbca=INFO
logging.level.org.springframework.ai=DEBUG
```

**Option 2: Environment Variable**
```bash
export OPENAI_API_KEY=your-api-key-here
```

**For Windows:**
```cmd
set OPENAI_API_KEY=your-api-key-here
```

### Build the Project

Build the application and download all dependencies:

```bash
# Using Maven wrapper (recommended)
./mvnw clean install

# Or using system Maven
mvn clean install
```

This will:
- Compile Java sources
- Download all Maven dependencies
- Install Node.js dependencies
- Build the frontend with Vite
- Run tests
- Package the application

### Run the Application

**Development Mode** (with hot reload):

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or simply (default goal is spring-boot:run)
./mvnw
```

**Production Mode**:

```bash
# Build production package
./mvnw -Pproduction package

# Run the JAR
java --add-opens java.base/sun.misc=ALL-UNNAMED \
     --add-opens java.base/java.nio=ALL-UNNAMED \
     -jar target/hbca-1.0-SNAPSHOT.jar
```

The application will start on **http://localhost:8080**

### Run Tests

Execute the test suite:

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=HotelBookingServiceTest

# Run with coverage
./mvnw clean verify
```

## Using the Application

### Access the Application

1. Open your browser and navigate to `http://localhost:8080`
2. You'll see a chat interface with a message input

### Sample Bookings

The application comes pre-loaded with sample bookings for testing:

| Booking # | First Name | Last Name | Hotel          | Room Type | Check-in | Check-out |
|-----------|------------|-----------|----------------|-----------|----------|-----------|
| 101       | Jack       | Bauer     | Marriot        | KING      | Today    | Today+2   |
| 102       | Chloe      | O'Brian   | Hilton         | QUEEN     | Today+2  | Today+4   |
| 103       | Kim        | Bauer     | Sheraton       | DOUBLE    | Today+4  | Today+6   |
| 104       | David      | Palmer    | Westin         | SUITE     | Today+6  | Today+8   |
| 105       | Michelle   | Dessler   | Four Seasons   | KING      | Today+8  | Today+10  |

### Sample Conversations

**Example 1: View Booking**
```
You: Hi, I'd like to check my booking
AI: Hello! I'd be happy to help you with that. To access your booking
    information, I'll need:
    1. Your booking number
    2. Your first name
    3. Your last name

You: My booking number is 101, first name Jack, last name Bauer
AI: [Shows booking details with check-in, check-out dates, room type, etc.]
```

**Example 2: Change Room Type**
```
You: I want to upgrade my room to a suite for booking 102, Chloe O'Brian
AI: [Checks terms and conditions via RAG]
    I can help you upgrade your room. According to our terms, room upgrades
    are subject to availability and may incur additional charges of $50 per
    night. Would you like to proceed?

You: Yes, please proceed
AI: Your room has been successfully upgraded to SUITE.
```

**Example 3: Cancel Booking**
```
You: Cancel booking 103 for Kim Bauer
AI: I've successfully cancelled your booking. You should receive a
    confirmation email shortly.
```

### Navigation Tips

- The AI will remember context throughout the conversation
- You can ask about hotel policies and terms at any time
- The AI will proactively verify your identity before making changes
- Responses stream in real-time for a natural chat experience

## Docker Support

### Build Docker Image

```bash
# Standard build
docker build -t hotel-booking-ai:latest .

# With commercial Vaadin license (if applicable)
docker build --secret id=proKey,src=$HOME/.vaadin/proKey \
             -t hotel-booking-ai:latest .
```

### Run Container

```bash
docker run -p 8080:8080 \
           -e OPENAI_API_KEY=your-api-key \
           hotel-booking-ai:latest
```

### Docker Compose (Optional)

Create a `docker-compose.yml`:

```yaml
version: '3.8'
services:
  hotel-booking-ai:
    build: .
    ports:
      - "8080:8080"
    environment:
      - OPENAI_API_KEY=${OPENAI_API_KEY}
      - SPRING_PROFILES_ACTIVE=production
```

Run with:
```bash
docker-compose up
```

## What's Next

This application provides a solid foundation for an AI-powered customer support system. Here are potential enhancements to consider:

### 1. **Enhanced AI Capabilities**
- **Multi-language Support**: Detect customer language and respond accordingly
- **Sentiment Analysis**: Detect frustrated customers and escalate to human agents
- **Voice Interface**: Add speech-to-text and text-to-speech capabilities
- **AI Model Selection**: Support multiple AI providers (Azure OpenAI, Claude, Gemini)

### 2. **Database & Persistence**
- **PostgreSQL Integration**: Replace H2 with production-grade database
- **Redis Cache**: Add caching layer for frequently accessed bookings
- **Chat History Storage**: Persist conversations for analytics and training
- **Vector Database**: Use Pinecone, Weaviate, or pgvector for better RAG performance

### 3. **Authentication & Security**
- **OAuth2/OIDC**: Integrate with external identity providers (Google, Microsoft)
- **JWT Tokens**: Secure API endpoints
- **Role-Based Access**: Add admin panel for support agents
- **Rate Limiting**: Prevent API abuse
- **API Key Rotation**: Secure OpenAI key management with HashiCorp Vault

### 4. **Booking System Integration**
- **Real Hotel APIs**: Integrate with actual booking systems (Amadeus, Sabre)
- **Payment Gateway**: Add Stripe/PayPal for booking modifications
- **Email Notifications**: Send confirmation emails via SendGrid/AWS SES
- **Calendar Integration**: Add to Google Calendar/Outlook

### 5. **Advanced Features**
- **Multi-tenant Support**: Support multiple hotel chains
- **Agent Handoff**: Seamlessly transfer complex issues to human agents
- **Booking Recommendations**: AI-powered upselling and cross-selling
- **Predictive Analytics**: Forecast customer needs based on conversation patterns
- **A/B Testing**: Test different AI prompt strategies

### 6. **Monitoring & Observability**
- **Application Performance Monitoring**: Integrate New Relic, DataDog, or Dynatrace
- **Distributed Tracing**: Add OpenTelemetry for microservices readiness
- **AI Token Usage Tracking**: Monitor and optimize OpenAI API costs
- **User Analytics**: Track conversation success rates and user satisfaction
- **Error Tracking**: Sentry or Rollbar integration

### 7. **User Experience**
- **Mobile App**: Native iOS/Android applications
- **Rich Media**: Support image uploads (booking confirmations, IDs)
- **Typing Indicators**: Show when AI is processing
- **Quick Replies**: Suggest common actions as buttons
- **Conversation Export**: Allow users to download chat transcripts

### 8. **Testing & Quality**
- **Integration Tests**: Comprehensive API testing with WireMock for OpenAI
- **E2E Tests**: Playwright/Cypress for frontend testing
- **Load Testing**: JMeter/Gatling for performance validation
- **AI Response Testing**: Validate AI outputs against expected behaviors
- **Chaos Engineering**: Test resilience with chaos mesh

### 9. **DevOps & Infrastructure**
- **CI/CD Pipeline**: GitHub Actions, GitLab CI, or Jenkins
- **Kubernetes Deployment**: Helm charts for orchestration
- **Infrastructure as Code**: Terraform for cloud resources
- **Blue-Green Deployments**: Zero-downtime releases
- **Auto-scaling**: Horizontal pod autoscaling based on traffic

### 10. **Compliance & Privacy**
- **GDPR Compliance**: Data deletion and export capabilities
- **Audit Logging**: Track all booking modifications
- **Data Anonymization**: Mask sensitive information in logs
- **Terms Acceptance**: Track customer consent for terms of service

### 11. **Business Intelligence**
- **Admin Dashboard**: Vaadin admin panel for viewing all bookings
- **Reporting**: Generate booking trends and AI usage reports
- **Customer Insights**: Analyze common support requests
- **Cost Analysis**: Track AI API usage and optimize spending

### Contributing Ideas
Feel free to fork this repository and implement any of these features. Pull requests are welcome!

## License

This project is licensed under the MIT License – see the [LICENSE](LICENSE.md) file for details.

## Author

**Mohamed Taman**
- Email: mohamed.taman@gmail.com
- Role: Solutions Architect & Java Software Architect
- GitHub: [@mohamed-taman](https://github.com/mohamed-taman)

---

**Built with ❤️ using Spring AI and Vaadin**
