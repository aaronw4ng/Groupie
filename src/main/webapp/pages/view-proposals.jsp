<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
  <title>groupie | proposals</title>
  <link rel="stylesheet" href="../styles/index.css" />
  <link rel="stylesheet" href="../styles/proposal.css" />
  <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>

<body>
  <div id="page-container">
    <div class="header">
      <h1>groupie</h1>
      <div class="header-links">
        <a href="#" class="highlight-link">Proposals</a>
        <a href="../index.jsp">Logout</a>
      </div>
    </div>

    <!-- Sent Proposals Container -->
    <div>
    <h1>Sent Proposals</h1>
      <div class="border">
        <h2>Proposal Name 1</h2>
        <h3>Events</h3>
          <ul><a href="#">Event 1</a></ul>
          <ul><a href="#">Event 2</a></ul>
        <h3>Invitee Responses</h3>
          <ul class="strong-result">User One
            <div class="btn-view">
              <button class="selected">Yes</button>
              <button>No</button>
            </div>
          </ul>

          <ul class="strong-result">Other User
            <div class="btn-view">
              <button>Yes</button>
              <button class="selected">No</button>
            </div>
          </ul>

          <ul class="strong-result">User 1
            <div class="btn-view">
              <button>Yes</button>
              <button>No</button>
            </div>
          </ul>
      </div>
        <div class="border">
          <h2>Proposal Name 2</h2>
          <h3>Events</h3>
          <ul><a href="#">Event 1</a></ul>
          <ul><a href="#">Event 2</a></ul>
          <h3>Invitee Responses</h3>
          <ul class="strong-result">User Friend
            <div class="btn-view">
              <button>Yes</button>
              <button class="selected">No</button>
            </div>
          </ul>

          <ul class="strong-result">ABC User
            <div class="btn-view">
              <button>Yes</button>
              <button class="selected">No</button>
            </div>
          </ul>

          <ul class="strong-result">User
            <div class="btn-view">
              <button class="selected">Yes</button>
              <button>No</button>
            </div>
          </ul>
        </div>

    <!-- Received Proposals Container -->
    <div>
      <h1>Received Proposals</h1>
      <div class="border">
        <h2><a href="#">Proposal Name 1</a></h2>
        <h3 class="strong-result">Sent by: Name</h3>
        <h3>Events</h3>
        <ul><a href="#">Event 1</a></ul>
        <ul><a href="#">Event 2</a></ul>
        <h3>Current Response: </h3>
          <div class="btn-view">
            <button class="selected">Yes</button>
            <button>No</button>
          </div>
      </div>
    </div>

</div>
</body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>