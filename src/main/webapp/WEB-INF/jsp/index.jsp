<html>

<head>
<title>Shorten Url</title>
</head>
<style>
.wd{ width : 800px;}
</style>
<body>
    <font color="red">${errorMessage}</font>
    <form method="post">
         Url : <input type="text" name="url" value="${url}" class="wd"/>
        <input type="submit" name="convert"  text="convert" value="convert to short url"/>
        <input type="submit" name="invert"  text="invert" value="invert to long url"/>
    </form>
    <br/>
   Short Url : <font color="blue">${shorturl}</font>
      Full Url : <font color="blue">${fullurl}</font>
</body>

</html>