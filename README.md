<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">Collector: Multi-type collection tracker</h3>

  <p align="center">
    Collector allows for the storage and tracking of multiple collections of items.
    Items can be filtered by type, name, and other details.
    Includes additional functionality for some types of items; Scryfall API integration for MTG card information, links to Bricklink for LEGO sets.
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Collector is designed to allow a user with multiple collections to store a record of them all in one place.<br>
It allows the collection to be viewed freely by guests, and includes a secure login to allow an admin user to add, remove, or edit items.<br><br>
Items can be filtered by type (eg. MTG card, LEGO set, Comic book), name, and other details.<br>
The system is flexible and will allow user to add any type of item, and the front end will dynamically update to show new categories as soon as an item is added.<br><br>
Includes additional functionality for some types of items; Scryfall API integration for MTG card information, links to Bricklink for LEGO sets.
<br><br>
The application interfaces with the MySQL database to store collection data, and serves front end pages for interfacing with this data, as well as the main website homepage.
<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [Gson](https://github.com/google/gson)
* [RetroFit](https://square.github.io/retrofit/)
* [Thymeleaf](https://www.thymeleaf.org/)
* [Bootstrap](https://getbootstrap.com)


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Installation
1. Clone the repo
   ```sh
   git clone https://github.com/suracki/mediscreenapplication.git
   ```
2. Start the application running in Docker
   Ensure that Docker is installed and running
   ```
   https://docs.docker.com/get-docker/
   ```
   Use the provided docker-compose.yml to build and start the application
   ```
   docker-compose up -d
   ```
3. Access the application via web browser
   Once the application is running it can be accessed via web browser
   ```
   Default:
   https://localhost:8080


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

### Front End

The application serves a Front End UI using Thymeleaf and Bootstrap, which can be accessed via the following URLs:

/ -> Main homepage, welcome page with links to guide user through interface<br>
/admin/manage -> Admin homepage<br>
/guest/viewHome -> Guest homepage<br>


<p align="right">(<a href="#top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Simon Linford - simon.linford@gmail.com

Project Link: [https://github.com/Suracki/Collector](https://github.com/Suracki/Collector)

<p align="right">(<a href="#top">back to top</a>)</p>
