<html>
<body>
<h2>Liked Profiles</h2>
<ul>
    <#list likedProfiles as profile>
        <li>${profile.name} - <img src="${profile.photoUrl}" alt="Photo"></li>
    </#list>
</ul>
<br>
<a href="/users">Go back to profiles</a>
</body>
</html>
