# Weekly Menu Server

<br />
<p align="center">
 <a href="https://github.com/eliasjunior/weekly-menu/blob/master/docs/favicon.png">
    <img src="docs/favicon.png" alt="Logo" width="200px" height="225px">
 </a>

  <h3 align="center">Weekly Menu</h3>

  <p align="center">
    It's a web application to make the shopping list from recipes.
    <br />
    <br />
  </p>
</p>
<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
        <a href="#architecture">Architecture</a>
        <ul>
          <li><a href="#components-boundaries">Components boundaries</a></li>
          <li><a href="#file-structure">File Structure</a></li>
          <li><a href="#useCases">UseCases</a></li>
          <li><a href="#ds-input-output">Ds Input Output</a></li>
        </ul>
    </li>
  </ol>
</details>

## About the project

This app is not just a shopping list app, it generates a list of products based on your weekly menu, 
so you don't need to worry about on what product goes into your list, 
the app takes care of it and generates for you based on the recipe's product,
(See [UI](https://github.com/eliasjunior/weekly-menu-react))


### Built With
* [Spring Boot](https://spring.io/projects/spring-boot) 
* [Clean architecture approach](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) 


Front-end [React UI](https://github.com/eliasjunior/weekly-menu-react)

### Architecture
<p align="center">
    <a href="https://github.com/eliasjunior/home-video-docs/blob/main/docs/clean-architecture.png">
        <img src="docs/clean-architecture.png" alt="architecture" width="100%" height="100%">
    </a>
</p>

Software architecture is one of my favorites topics and putting theory and practice together is challenging but also fun,
and when it comes to software engineering. Robert C. Martin (aka Uncle Bob) has developed his vision of a
clean architecture in his book, that I highly recommend.

### Components boundaries
<p align="center">
    <a href="https://github.com/eliasjunior/home-video-docs/blob/main/docs/design-web.png">
    </a>
</p>

### File structure
<p align="center">
    <a href="https://github.com/eliasjunior/home-video-docs/blob/main/docs/file-structure.png">
        <img src="docs/file-structure.png" alt="file structure" width="100%" height="100%">
    </a>
</p>

### UseCases

In order simplify the development I've chosen to not create the domain/entities module for this first stage,
as the code evolves I can come back and rethink  to add more layers as it need, the domain/entities are in the useCase module

### Ds Input Output

Data structure output and input are not implemented yet, for now I'm just passing the entities(domain) for simplicity









    
