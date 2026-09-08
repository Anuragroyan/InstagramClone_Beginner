📸 Instagram Clone

Instagram Clone is a Kotlin-based social media application inspired by Instagram, built with Jetpack Compose and Hilt Dependency Injection.

The application provides a social-media experience with features such as user authentication, account management, profile customization, profile picture uploads, post creation, reels creation, and content management.

The project focuses on implementing modern Android development practices with a clean and responsive UI/UX while maintaining a scalable and maintainable application architecture.

🎯 Why This Project?

Social media applications involve multiple interconnected features such as authentication, user profiles, media uploads, content creation, and content management.

This project recreates these core concepts in a native Android environment to demonstrate how a modern social media application can be structured and developed using Kotlin, Jetpack Compose, Hilt, and clean architectural practices.

The project provides hands-on experience with managing user-driven content while keeping the codebase modular and scalable.

✨ Features

* 🔐 User registration and authentication
* 👤 Account creation and management
* ✏️ Update account information
* 🧑‍💻 Profile management
* 🖼️ Profile picture upload
* 📸 Create image posts
* 🎬 Create reels
* ✏️ Manage published content
* 🗑️ Delete posts and content
* 👀 View user content
* 📱 Clean and responsive UI/UX
* 🧩 Reusable Jetpack Compose components
* 💉 Hilt Dependency Injection
* 🔄 Reactive UI state management
* 🏗️ Scalable Android architecture
* ⚡ Modern Android development practices

🔄 Application Workflow

Create Account → Login → Setup Profile → Upload Profile Picture → Create Post / Reel → Manage Content → View Profile

🧩 Main Modules

🔐 Authentication

Users can create an account and authenticate themselves before accessing the application’s social features.

Authentication provides the foundation for user-specific profiles and content management.

👤 Account Management

Users can manage their account information after registration.

Typical operations include:

* Create account
* Login
* Update account information
* Manage profile details
* Maintain authenticated user state

🧑‍💻 Profile Management

Each user has a dedicated profile containing their personal information and published content.

Users can update their profile information and upload a profile picture.

🖼️ Profile Picture Upload

Users can select and upload a profile picture to personalize their account.

The uploaded image is associated with the user’s profile and displayed throughout the application.

📸 Post Management

Users can create and manage image-based posts.

Post operations include:

Create → Read → Update → Delete

A post can contain information such as:

* Image
* Caption
* User information
* Timestamp
* Content metadata

🎬 Reels

The application includes a reels-style content feature for creating and managing short-form video content.

Users can publish reels and manage their uploaded content within the application.

🗂️ Content Management

Users can manage their published content by viewing and deleting posts or reels when required.

Create Content → Publish → View → Manage → Delete

💉 Dependency Injection

The project uses Hilt Dependency Injection to manage dependencies throughout the application.

UI → ViewModel → Repository → Data Source
             ↓
            Hilt

Hilt helps provide dependencies automatically and reduces manual object creation, making the application easier to maintain and test.

🏗️ Application Architecture

The project follows a modern Android architecture that separates UI, state management, business logic, and data operations.

Jetpack Compose UI → ViewModel → Repository → Data Source / Backend

This separation keeps the codebase modular and makes individual features easier to develop, test, and maintain.

🎨 UI / UX

The application is designed using Jetpack Compose with a focus on a clean and responsive social-media experience.

The UI uses reusable Compose components to maintain consistency across screens while keeping the interface intuitive and easy to navigate.

🛠️ Tech Stack

Kotlin • Jetpack Compose • Hilt • Android SDK • MVVM • Repository Pattern • Kotlin Coroutines • State Management • Modern Android Architecture

Add Firebase, REST APIs, or specific media/storage libraries here if they are part of your actual implementation.

📁 Project Structure

InstagramClone/
 → app/src/main/java/ • ui/ • screens/ • components/ • viewmodel/ • repository/ • model/ • di/
 → app/src/main/res/ • drawable/ • mipmap/ • values/
 → AndroidManifest.xml • build.gradle.kts • settings.gradle.kts • README.md

🚀 Getting Started

Prerequisites

Make sure you have the following installed:

* Android Studio
* JDK
* Android SDK
* Android Emulator or physical Android device
* Required backend/database services, if applicable

Installation

Clone the repository:

git clone <your-repository-url>

Navigate to the project:

cd InstagramClone

Open the project in Android Studio and allow Gradle to sync.

Run the Application

Select an Android Emulator or connected physical device and click:

Run ▶

Alternatively, from the project directory:

./gradlew installDebug

To generate a debug APK:

./gradlew assembleDebug

🔒 Authentication & Data Security

Authentication is used to protect user-specific account and content operations.

The application should ensure that:

* Only authenticated users can access protected features.
* User-specific content is associated with the correct account.
* Profile and content operations are authorized.
* Uploaded media is handled securely.
* Sensitive credentials are never stored as plain text.

If a backend or Firebase is used, appropriate authentication and database/storage security rules should be configured.

💡 Real-World Use Case

A user wants to create a social-media profile and share content.

They can:

1. Create an account.
2. Log in.
3. Update their profile.
4. Upload a profile picture.
5. Create an image post.
6. Publish a reel.
7. View their uploaded content.
8. Manage or delete existing content.

This creates a complete:

Account → Profile → Media Upload → Post/Reel → Content Management

workflow.

🎯 Project Goals

The main goals of this project are:

* Build a functional Instagram-inspired social media application.
* Develop a native Android application using Kotlin.
* Create a modern UI using Jetpack Compose.
* Implement user authentication and account management.
* Implement profile customization and media uploads.
* Implement post and reels creation.
* Support content management operations.
* Practice dependency injection using Hilt.
* Apply MVVM and Repository-based architecture.
* Build reusable Compose UI components.
* Practice Kotlin Coroutines and reactive state management.
* Follow modern Android development practices.
* Build a scalable portfolio project demonstrating real-world Android development.

📌 Portfolio Highlights

This project demonstrates practical experience with:

Kotlin • Jetpack Compose • Android Development • MVVM • Repository Pattern • Hilt Dependency Injection • Kotlin Coroutines • State Management • Authentication • Account Management • Profile Management • Image Upload • Post Creation • Reels • CRUD Operations • Content Management • Media Handling • Clean UI/UX • Scalable Android Architecture
