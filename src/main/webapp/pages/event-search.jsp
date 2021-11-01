<!DOCTYPE html>
<html>
<head>
    <title>Group Date Planner</title>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/events.css" />
    <script src="https://kit.fontawesome.com/d4a13a138b.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
    <body>
        <div class="header">
            <h1>groupie</h1>
            <div class="header-links">
                <a href="#" class="highlight-link">Proposals</a>
                <a href="../index.jsp">Logout</a>
            </div>
        </div>

        <!-- Search Container -->
        <div class="event-search-container">
            <form id="event-search-form">
                <div class="search-row">
                    <input id="event-search-input" type="text" placeholder="Event, artist, team">
                    <button id="event-search-button" onclick="handleSubmit(event)"><i class="fas fa-search"></i></button>
                </div>
                <div class="search-row">
                    <div class="search-item">
                        <p class="search-input-label">start date</p>
                        <input class="search-input date-input" id="event-start-input" type="text" placeholder="mm/dd/yy">
                    </div>
                    <div class="search-item">
                        <p class="search-input-label">end date</p>
                        <input class="search-input date-input" id="event-end-input" type="text" placeholder="mm/dd/yy">
                    </div>

                    <div class="vertical-line"></div>
                    
                    <select class="search-input" id="event-genre-input">
                        <option value="">genre</option>
                        <option value="">Blah</option>
                        <option value="">Blah</option>
                        <option value="">Blah</option>
                    </select>

                    <div class="vertical-line"></div>

                    <div class="search-item">
                        <p class="search-input-label">city</p>
                        <input class="search-input" id="event-city-input" type="text" placeholder="ex: Los Angeles">
                    </div>
                    
                    <div class="search-item">
                        <p class="search-input-label">zipcode</p>
                        <input class="search-input" id="event-zip-input" type="text" placeholder="ex: 90007">
                    </div>
                    
                </div>

            </form>
        </div>

        <!-- TODO: Add a DISPLAY RESULTS container -->
        
        <div id="footer">
            <p>team 27</p>
        </div>
        <script src="../scripts/event-search.js"></script>
    </body>
</html>