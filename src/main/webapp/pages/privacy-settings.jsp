<!DOCTYPE html>
<html>

<head>
  <title>groupie | privacy settings</title>
  <link rel="stylesheet" href="../styles/index.css" />
  <link rel="stylesheet" href="../styles/proposal.css" />
  <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;500;800&display=swap');
    </style>
</head>

<body>
  <div id="page-container">
    <div class="header">
      <h1>groupie</h1>
      <div class="header-links">
        <a href="view-proposals.jsp">View Proposals</a>
        <a href="view-proposals.jsp">Create Proposal</a>
        <a href="#" class="highlight-link">Privacy</a>
        <a href="create-proposal.jsp">Logout</a>
      </div>
    </div>

    <!-- Blocked Users Container -->
    <div>
    <h1>blocked users</h1>
      <div class="border">
        <h2>List of users</h2>
        <p> <a href="#">add </a> | <a href="#">remove</a>  </p>
      </div>

    <!-- Availability Container -->
    <div>
      <h1>availability</h1>
      <div class="border">
        <h2>Set yourself as unavailable to stop receiving proposals for the selected amount of time.</h2>

          <div class="btn-view">
            <button class="selected">On</button>
            <button>Off</button>
          </div>
        <h3>Set Time: </h3>
      </div>
    </div>

</div>
</body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>