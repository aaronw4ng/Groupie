<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/event-details.css" />
    <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <title>groupie | event details</title>
</head>
<body>
  <div class="header">
    <h1>groupie</h1>
    <div class="header-links">
      <a href="#" class="highlight-link">Proposals</a>
      <a href="../index.jsp">Logout</a>

    </div>
  </div>

  <div id="event-details-page">
    <div class="details-row">
        <h1 id="event-name-header">event name</h1>
        <div class="btn-back-container">
            <p>back to proposal</p>
            <button id="btn-back" onclick="handleBackBtnClick(event)"><i class="fas fa-undo-alt"></i></button>
        </div>
    </div>

    <hr>

    <h1 class="page-title"><i class="fas fa-info-circle"></i> event details</h1>
    <div id="details-container">
        <div class="details-section">
            <p class="section-title">start date</p>
            <p id="start-date" class="section-info">mm-dd-yyyy</p>
        </div>
        <div class="details-section">
            <p class="section-title">end date</p>
            <p id="end-date" class="section-info">mm-dd-yyyy</p>
        </div>
        <div class="details-section">
            <p class="section-title">city</p>
            <p id="city" class="section-info">Los Angeles</p>
        </div>
        <div class="details-section">
            <p class="section-title">address</p>
            <p id="address" class="section-info">Hellman Way</p>
        </div>
        <div class="details-section">
            <p class="section-title">venue</p>
            <p id="venue" class="section-info">Venue Name</p>
        </div>
        <div class="details-section">
            <p class="section-title">event link</p>
            <a id="event-link" class="section-info" target="_blank" href="#">ticketmaster page</a>
        </div>

    </div>

    <hr>
    <h1 class="page-title">responses</h1>
    <div id="responses-container">
      <!-- USER RESPONSE -->
      <div id="user-response-card">
        <div class="response-card-col">
          <h1 id="username-header"><i class="fas fa-user-edit"></i></h1>
          <h1 id="username">yourUsername</h1>
        </div>

        <hr id="response-card-split">
            
        <div class="response-card-col">
          <p class="user-response-prompt">are you available?</p>
          <div class="availability-container">
            <button id="mark-available" class="btn-availability" onclick="handleAvailableClick(event)"><i class="fas fa-check-circle"></i></button>
            <button id="mark-maybe" class="btn-availability" onclick="handleAvailableClick(event)"><i class="fas fa-question-circle"></i></button>
            <button id="mark-unavailable" class="btn-availability" onclick="handleAvailableClick(event)"><i class="fas fa-times-circle"></i></button>
          </div>
        </div>

        <div class="response-card-col">
          <p class="user-response-prompt">rate your excitement</p>
          <div class="excitement-container">
            <button data-star="1" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
            <button data-star="2" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
            <button data-star="3" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
            <button data-star="4" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
            <button data-star="5" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
          </div>
        </div>

        <div class="save-container">
          <button id="btn-save-user-resp" onclick="handleSaveUserRespClick(event)">save response</button>
        </div>
        

      </div>
       <!-- END USER RESPONSE -->

       <!-- OTHER RESPONSES -->
       <!-- Example of available user -->
       <div class="other-response-card">
        <div class="response-card-col">
          <h1 class="other-username" id="other-user-1">username1</h1>
        </div>
        <div class="response-card-col">
          <p class="user-response-prompt">available?</p>
          <i class="fas fa-check-circle response-available"></i>
        </div>
        <div class="response-card-col">
          <p class="user-response-prompt">excitement?</p>
          <div class="excitement-container">
            <i class="fas fa-star other-excitement-star"></i>
            <i class="fas fa-star other-excitement-star"></i>
            <i class="fas fa-star other-excitement-star"></i>
            <i class="far fa-star other-excitement-star"></i>
            <i class="far fa-star other-excitement-star"></i>
          </div>
        </div>
       </div>

       <!-- Example of unavailable user -->
       <div class="other-response-card">
        <div class="response-card-col">
          <h1 class="other-username" id="other-user-1">username2</h1>
        </div>
        <div class="response-card-col">
          <p class="user-response-prompt">available?</p>
          <i class="fas fa-times-circle response-unavailable"></i>
        </div>
        <div class="response-card-col">
          <p class="user-response-prompt">excitement?</p>
          <div class="excitement-container">
            <i class="far fa-star other-excitement-star"></i>
            <i class="far fa-star other-excitement-star"></i>
            <i class="far fa-star other-excitement-star"></i>
            <i class="far fa-star other-excitement-star"></i>
            <i class="far fa-star other-excitement-star"></i>
          </div>
        </div>
       </div>

      

    </div>
   

      
  </div>

  

  <div id="footer">
    <p>team 27</p>
  </div>

  <jsp:include page="autologout.jsp"></jsp:include>
  <script src="../scripts/autologout.js"></script>
  <script src="../scripts/event-details.js"></script>

</body>
</html>