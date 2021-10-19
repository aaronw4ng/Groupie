<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/account.css" />
</head>
<body>
    <div class="header">
        <h1>grouping</h1>
    </div>
    <div class="account-page">
        <h1>Create Account</h1>
        <p>Create an account to get started with the scheduling app!</p>
        <form class="account-form">
            <p class="input-header">Username</p>
            <input class="field" type="text" id="input-username" placeholder="Username">
            <p class="input-header">Password</p>
            <input class="field" type="password" id="input-password" placeholder="Password">
            <p class="input-header">Confirm Password</p>
            <input class="field" type="password" id="re-input-password" placeholder="Confirm Password">
            <div class="btn-row">
                <button id="btn-create-account">Create Account</button>
                <button id="btn-back">Back</button>
            </div>
            
        </form>
    </div>

    <div id="footer">
        <p>team 27</p>
    </div>

    <script src="../scripts/create-account.js"></script>
</body>
</html>