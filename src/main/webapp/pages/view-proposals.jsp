<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
  <title>groupie | proposals</title>
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
        <a href="#" class="highlight-link">View Proposals</a>
        <a href="../index.jsp">Logout</a>
      </div>
    </div>


    <!-- Received Proposals Container -->
    <div style="text-align:center;">
        <div>
        <h6>my received proposals</h6>
    </div>

      <!-- TODO: Link to proposal details page -->
      <div id="border">
        <h4>Proposal Name 1</h4>
          <h3 class="not-finalized"style="color:darkred;">not finalized</h3>
      </div>

        <div id="border">
          <h4>Proposal Name 2</h4>
          <h3 class="finalized" style="color:goldenrod;">April 3, 2023</h3>
        </div>
        </div>

        <div id="proposal-results-container"></div>

    <!-- Filter/Sort Events Container -->
    <div style="text-align:center;" id="events-filters">
    <h6>all events</h6>
    <h3>filter: </h3>
        <button class="view-proposals" onclick="toggle(this);" id="final-btn"value="OFF">finalized</button>
        <button class="view-proposals" onclick="toggle(this);" id="unfinal-btn" value="OFF">unfinalized</button>
    <h3>sort: </h3>
        <button class="view-proposals" onclick="toggle(this);" id="newest-btn">newest</button>
        <button class="view-proposals" onclick="toggle(this);" id="oldest-btn">oldest</button>
        <h2 onclick="clearFilters()" id="events-filters"> Clear Filters </h2>
    </div>

    <!-- Example -->
      <div class="border">
        <h4>Proposal Name 1</h4>
        <h3>events</h3>
          <ul><a href="#">Event 1</a></ul>
          <ul><a href="#">Event 2</a></ul>
        <h3>date/time:</h3>
        <ul><p class="strong-result">not finalized</p></ul>
      </div>

      <div id="proposal-results-container"></div>

</div>
    <script src="../scripts/view-proposals.js"></script>
</body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>