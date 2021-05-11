# Architecture Decisions

<br />
<p align="center">
 <a href="https://github.com/eliasjunior/weekly-menu/blob/master/docs/architecture.png">
    <img src="docs/clean-architecture.png" alt="Logo" width="200px" height="225px">
 </a>

<h3 align="center">Clean Architecture</h3>

  <p align="center">
    Software architecture is one of my favorites topics and putting theory and practice together is challenging but also and fun,
    and when it comes to software engineering. Robert C. Martin (aka Uncle Bob) has developed his vision of a 
    clean architecture in his book, that I highly recommend.
    <br />
    <br />
  </p>
</p>
<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project Decisions</a>
      <ul>
        <li><a href="#built-with">Just useCase</a></li>
        <li><a href="#built-with">Mappers</a></li>
      </ul>
    </li>
  </ol>
</details>

## Components boundaries
<p align="center">
    <a href="https://github.com/eliasjunior/home-video-docs/blob/main/images/design.png">
        <img src="docs/design-web.png" alt="Logo" width="100%" height="100%">
    </a>
</p>

</br>

<!-- ABOUT THE PROJECT -->

## About The Project

### Only useCase layer

In order simplify the development I've chosen not to create the domain/entities module for this first stage, 
as the code evolves I can come back and rethink  to add more layers as it need, the domain/entities are in the useCase module

### DS Input/Output

Output date is not full implemented yet, for now I'm just passing the entities(domain) for simplicity



    
