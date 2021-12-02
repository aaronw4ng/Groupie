<!DOCTYPE html>
<html>

<head>
  <title>groupie | search</title>
  <link rel="stylesheet" href="../styles/index.css" />
  <link rel="stylesheet" href="../styles/events.css" />
  <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>

<body onload="setFilters()">
  <div id="page-container">
    <div class="header">
      <h1>groupie</h1>
      <div class="header-links">
      <a id="view-proposals-btn" href="view-proposals.jsp">View Proposals</a>
      <a id="create-proposal-btn" href="create-proposal.jsp">Create Proposal</a>
      <a id="privacy-settings-btn" href="privacy-settings.jsp">Privacy Settings</a>
      <a id="logout-btn" href="../index.jsp">Logout</a>
      </div>
    </div>

    <!-- Search Container -->
    <!-- <div class="btn-back-container">
      <p>back to your proposal</p>
      <button id="btn-back"><i class="fas fa-undo-alt"></i></button>
    </div> -->
    <div class="event-search-container">
      <form id="event-search-form">
        <div class="search-row">
          <input id="event-search-input" type="text" placeholder="Event, artist, team">
          <button id="event-search-button" onclick="handleSubmit(event)"><i class="fas fa-search"></i></button>
        </div>

        <button id="toggleFilters" type="button">show filters </button>
        <div id="search-filters">
          <div class="search-item">

            <div id="start-date">
              <label for="start" id="datelabel">start date</label>
              <input type="date" id="start" name="date-start" value="" min="2021-11-03" max="2100-11-03"/>
            </div>
          </div>
          <div class="search-item">
            <div id="end-date">
              <label for="end" id="datelabel">end date</label>
              <input type="date" id="end" name="date-end" value="" min="2021-11-03" max="2100-11-03" />
            </div>
          </div>

          <div class="search-item">
            <p class="search-input-label">city</p>
            <input class="search-input" id="event-city-input" type="text" placeholder="ex: Los Angeles" />
          </div>

          <div class="search-item">
            <p class="search-input-label">zipcode</p>
            <input class="search-input" id="event-zip-input" type="text" placeholder="ex: 90007" />
          </div>

          <div class="search-item">
            <p class="search-input-label">genre</p>
            <select class="search-input" id="event-genre-input">
              <option selected disabled value="">Genres</option>
              <option value="Pop">Pop</option>
              <option value="Jazz">Jazz</option>
              <option value="Classical">Classical</option>
              <option value="Rock">Rock</option>
              <option value="Soccer">Soccer</option>
              <option value="Football">Football</option>
              <option value="Basketball">Basketball</option>
              <option value="Theatre">Theatre</option>
              <option value="Comedy">Comedy</option>
              <option value="Magic & Illusion">Magic & Illusion</option>
            </select>
          </div>
        </div>
    </div>

    </form>
  </div>

  <hr>

  <!-- TODO: Add a DISPLAY RESULTS container -->
  <div class="event-results-container">
    <p id="event-search-result-count"></p>
  </div>


  </div>
  <div id="footer">
    <p>team 27</p>
  </div>
  <script src="../scripts/event-search.js"></script>
</body>
  <jsp:include page="autologout.jsp"></jsp:include>
</html>