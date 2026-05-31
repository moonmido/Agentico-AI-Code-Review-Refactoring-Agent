# 🤖 Agentico — AI Code Review & Refactoring Agent

> A **Spring Boot + Embabel** AI Agent that analyzes source code, detects security vulnerabilities, and generates improved, clean versions of the code using LLM-powered reasoning.

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![Embabel](https://img.shields.io/badge/Embabel%20Agent-0.4.0-blue)](https://github.com/embabel/embabel-agent)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [Example](#-example)
- [Project Structure](#-project-structure)
- [How It Works](#-how-it-works)
- [Contributing](#-contributing)
- [License](#-license)

---

## 📖 Overview

**Agentico** is an intelligent, agent-based application built on top of **Spring Boot** and the **Embabel AI Agent framework**. It acts as an automated code reviewer and refactoring assistant: you submit source code (or a snippet), and the agent analyzes it for bugs, security vulnerabilities, and poor coding practices, then produces a cleaner, more secure version of the code.

The agent uses **LLM-powered reasoning** (via OpenAI) and an interactive **shell interface**, allowing developers to interact with it directly from the command line.

---

## ✨ Features

- 🔍 **Security Issue Detection** — Identifies vulnerabilities such as hardcoded credentials, command injection, SQL injection, and insecure API usage.
- 🛠️ **Code Refactoring** — Produces a cleaned-up, improved version of the submitted code while preserving the original intent.
- 🐚 **Interactive Shell** — Powered by `embabel-agent-starter-shell`, giving you a real-time CLI to interact with the agent.
- ⚡ **Reactive & Non-blocking** — Built with Spring WebFlux for efficient, reactive request handling.
- 🤖 **LLM-Backed Reasoning** — Integrates with OpenAI via the Embabel agent framework to perform nuanced code analysis.
- 📋 **Structured Output** — Provides clear, actionable feedback and refactored code output.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Agentico Agent                        │
│                                                              │
│   ┌──────────────┐    ┌─────────────────┐    ┌───────────┐  │
│   │  Shell CLI   │───▶│  Embabel Agent  │───▶│  OpenAI   │  │
│   │  Interface   │    │    Framework    │    │    LLM    │  │
│   └──────────────┘    └─────────────────┘    └───────────┘  │
│          │                    │                              │
│          ▼                    ▼                              │
│   ┌──────────────┐    ┌─────────────────┐                   │
│   │  User Input  │    │  Code Analyzer  │                   │
│   │  (Code/Cmd)  │    │  & Refactorer   │                   │
│   └──────────────┘    └─────────────────┘                   │
│                                │                             │
│                                ▼                             │
│                    ┌─────────────────────┐                   │
│                    │  Security Report +  │                   │
│                    │  Refactored Code    │                   │
│                    └─────────────────────┘                   │
└─────────────────────────────────────────────────────────────┘
```

---

## 🧰 Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Core language |
| Spring Boot | 3.5.14 | Application framework |
| Embabel Agent (OpenAI Starter) | 0.4.0 | LLM agent integration |
| Embabel Agent (Shell Starter) | 0.4.0 | Interactive CLI shell |
| Spring WebFlux | Managed by Boot | Reactive web layer |
| Maven | Wrapper included | Build tool |

---

## ✅ Prerequisites

Before running Agentico, make sure you have the following installed:

- **Java 21+** — [Download here](https://adoptium.net/)
- **Maven 3.8+** — (or use the included `mvnw` wrapper)
- **OpenAI API Key** — [Get one here](https://platform.openai.com/api-keys)

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/moonmido/Agentico-AI-Code-Review-Refactoring-Agent.git
cd Agentico-AI-Code-Review-Refactoring-Agent
```

### 2. Set your OpenAI API key

Set the API key as an environment variable:

```bash
# Linux / macOS
export OPENAI_API_KEY=sk-your-api-key-here

# Windows (Command Prompt)
set OPENAI_API_KEY=sk-your-api-key-here

# Windows (PowerShell)
$env:OPENAI_API_KEY="sk-your-api-key-here"
```

Alternatively, add it to `src/main/resources/application.properties`:

```properties
spring.ai.openai.api-key=sk-your-api-key-here
```

> ⚠️ **Never commit your API key to source control.**

### 3. Build the project

```bash
./mvnw clean install
```

On Windows:

```bash
mvnw.cmd clean install
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The interactive shell will start and you can begin submitting code for review.

---

## ⚙️ Configuration

The application is configured via `src/main/resources/application.properties`. Key properties:

```properties
# OpenAI API Key (can also be set via environment variable)
spring.ai.openai.api-key=${OPENAI_API_KEY}

# Optional: customize the model (default is gpt-4o or similar)
# spring.ai.openai.chat.options.model=gpt-4o

# Logging output file
logging.file.name=Agentico.log
```

---

## 💻 Usage

Once the application is running, the embedded shell becomes active. You can interact with the agent using the following commands:

### Review code for security issues

```shell
shell:> x "find security issues in: user = input(); password = '123'; os.system('rm -rf /tmp/test')"
```

### Submit a full code snippet for review and refactoring

```shell
shell:> x "import os

user = input('Enter name: ')
print('Hello ' + user)

password = 'admin123'

os.system('echo ' + user)"
```

The agent will:
1. Analyze the submitted code for security vulnerabilities and bad practices.
2. Return a detailed report of the issues found.
3. Generate a refactored, secure version of the code.

### Exit the shell

```shell
shell:> exit
```

---

## 📝 Example

**Input:**

```python
import os

user = input("Enter name: ")
print("Hello " + user)

password = "admin123"

os.system("echo " + user)
```

**Agent Output (summary):**

> **Security Issues Found:**
> - 🔴 **Hardcoded credential**: `password = "admin123"` — credentials should never be hardcoded; use environment variables or a secrets manager.
> - 🔴 **OS command injection**: `os.system("echo " + user)` — user input is concatenated directly into a shell command, allowing arbitrary command execution.
>
> **Refactored Code:**
> ```python
> import os
> import shlex
>
> user = input("Enter name: ")
> print(f"Hello {user}")
>
> # Load credentials from environment variables
> password = os.environ.get("APP_PASSWORD")
>
> # Use subprocess with argument list to prevent injection
> import subprocess
> subprocess.run(["echo", user], check=True)
> ```

---

## 📁 Project Structure

```
Agentico-AI-Code-Review-Refactoring-Agent/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/Agentico/
│       │       ├── AgenticoApplication.java      # Spring Boot entry point
│       │       └── agent/                        # Agent logic & shell commands
│       └── resources/
│           └── application.properties            # App configuration
│
├── target/
│   └── classes/                                  # Compiled classes
│
├── Agentico.log                                  # Application log file
├── HELP.md                                       # Spring Boot help reference
├── LICENSE                                       # MIT License
├── mvnw / mvnw.cmd                              # Maven wrapper scripts
├── pom.xml                                       # Maven build configuration
└── README.md                                     # This file
```

---

## 🔬 How It Works

Agentico uses the **Embabel Agent Framework** — a Spring-native AI agent library — to define a goal-driven agent that:

1. **Receives input** from the interactive shell (code snippets or commands).
2. **Plans a reasoning chain** using the configured LLM (OpenAI) to break down the task into: parse code → identify issues → suggest fixes → generate refactored output.
3. **Executes the plan** by making structured calls to the OpenAI API.
4. **Streams the result** back to the shell using Spring WebFlux's reactive pipeline.

The shell integration (`embabel-agent-starter-shell`) allows the entire interaction to happen in a terminal without needing a web UI.

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/your-feature-name`
3. Make your changes and commit: `git commit -m "Add your feature"`
4. Push to your fork: `git push origin feature/your-feature-name`
5. Open a Pull Request.

Please make sure your code compiles and any new features are documented.

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Embabel Agent Framework](https://github.com/embabel/embabel-agent)
- [OpenAI](https://openai.com/)

---

*Built with ❤️ using Spring Boot & Embabel AI*
