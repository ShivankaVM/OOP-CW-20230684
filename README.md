**_Real-Time Event Ticketing System_**

A real-time ticket management system that simulates ticket releases by vendors and purchases by customers. The system leverages concurrency, multi-threading, and real-time updates to provide dynamic transaction handling and system monitoring through an interactive dashboard.


**Table of Contents**
1. Introduction
2. Features
3. Technology Stack
4. Requirements
5. Setup Instructions
6. Usage Instructions
7. Project Structure
8. API Endpoints
9. Testing
10. Troubleshooting
11. Credits


**Introduction**

This system demonstrates concurrent ticket management using the Producer-Consumer pattern with:

Vendors releasing tickets at configurable rates.
Customers purchasing tickets with customizable capacities.
Real-time transaction tracking and updates via WebSockets.
Live system monitoring through a user-friendly Angular dashboard.

**Features**

* Concurrent Ticket Release and Purchase: Multiple vendors and customers operate simultaneously. 
* Thread-Safe Ticket Management: Ensures data integrity using synchronization methods.
* User Interface: Built with Angular for real-time system configuration and status monitoring. 
* Persistent Configuration: Saves and loads configuration data from a JSON file.
* Logging and Error Handling: Logs key events and handles errors gracefully. 
* Optional Enhancements: VIP customers, real-time analytics, dynamic vendor/customer management.


**Technology Stack**

* Backend: Spring Boot (Java) for RESTful APIs and business logic.
* Frontend: Angular (TypeScript) for a dynamic web interface.
* Data Persistence: JSON for storing configuration data.


**Requirements**

* Java 17+ 
* Spring Boot 
* Angular 
* Maven
* Libraries:
          Gson (for JSON handling)
          Spring Web (for RESTful APIs)


**Setup Instructions**

Backend Setup (Spring Boot)

1. Clone the Repository
2. Run the Spring Boot application:
mvn spring-boot:run
The backend will be available at http://localhost:9090.


Frontend Setup (Angular)

1. Navigate to the frontend directory: cd C:\Users\94713\Desktop\frontend
2. Install dependencies: npm install
3. Start the Angular development server:
The frontend will be available at http://localhost:4200.


**Usage Instructions**

1. System Configuration
   On the first launch, configure the following settings:
Maximum Ticket Capacity: Total capacity of the ticket pool.

Vendor Settings:
Number of vendors.
Ticket release rates (e.g., tickets per second).

Customer Settings:
Number of customers.
Purchase capacities and retrieval rates (e.g., tickets per second).

Enter the following parameters in the configuration form:
     Total Number of Tickets
     Vendor Ticket Release Rate
     Customer Ticket Purchase Rate
     Maximum Ticket Capacity
Save Configuration: The settings are saved in a JSON file (config.json).

2. Dashboard Overview
   Dashboard Tab: Displays real-time system status, including available and total tickets.
   Vendors Tab: Allows management of vendor activities and monitoring of ticket releases.
   Customers Tab: Enables control over customer behaviors and purchase tracking.
   Transactions Tab: Shows detailed lifecycle of tickets from release to purchase.

3. Managing Vendors
   Add Vendors with the following details:
        Vendor ID
        Ticket release rate (e.g., tickets per second)
        Monitor Vendors:

    Start/stop individual vendors.
         Track vendor performance in real time.

4. Managing Customers
   Configure Customers with:
       Customer ID
       Maximum holding capacity
       Purchase rate (e.g., tickets per second)
   
   Track Customer Activity:
       View purchase statistics.
       Control customer operations dynamically.

5. Real-Time Features
   WebSocket Updates: Receive live ticket availability updates on the frontend.
   Concurrency Handling: Multi-threaded operations with thread-safe synchronization.
   Dynamic Simulation: Manage simultaneous vendor releases and customer purchases smoothly.


**Project Deliverables**

    Source Code: Organized in packages (models, controllers, services).

    Diagrams:
        Class Diagram: Outlines key classes (e.g., Vendor, Customer, TicketPool).
        Sequence Diagram: Illustrates interactions during ticket release and purchase processes.

    README File: Setup, usage, and troubleshooting instructions.

    Testing Report: Covers scenarios like concurrent customer operations and capacity breaches.

    Demonstration Video: Showcases system setup, configuration, and advanced features.





