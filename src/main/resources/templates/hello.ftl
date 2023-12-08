<html>
<body>
<#if currentProfile?exists>
    <form method="post" action="/users">
        <label>Name: ${currentProfile.name}</label><br>
        <img src="${currentProfile.photoUrl}" alt="Photo"><br>
        <button type="submit" name="choice" value="yes">Yes</button>
        <button type="submit" name="choice" value="no">No</button>
    </form>
    <br>
    <form method="get" action="/liked">
        <button type="submit">View Liked Profiles</button>
    </form>
<#else>
    <p>No more profiles to show.</p>
    <form method="get" action="/liked">
        <button type="submit">View Liked Profiles</button>
    </form>
</#if>
</body>
</html>