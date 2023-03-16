
<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="shortcut icon" type="image/png" href="<@spring.url '/images/favicon.png'/>" />
    <meta charset="UTF-8">
    <title>Vanilla Periodic MonitoringTools</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<script src="vendors/jQuery/jquery-3.4.1.min.js"></script>
	
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/main.css'/>" />
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/fonts.css'/>" />
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/giz.css'/>" />    

    <script type='text/javascript' src="<@spring.url '/js/main.js'/>" ></script>

</head>
<body class="glogin-body">

    <section class="main formContact">
        <div class="gforget">
            <h2 class="">Votre contact</h2>

            <@spring.bind "useremail"/>
            <#if useremail?? && noErrors??>
            <h3> Votre contact est mis à jour </h3>
            <ul class="contactList">
                <li> <span>Name :</span> ${agent.name} </li>
                <li> <span>Email :</span> ${agent.email} </li>
                <li> <span>Message :</span> ${agent.message} </li>
            </ul> 
            <div class="btn-row btn-submit">
                <a class="gbtn" href="<@spring.url '/login' />">Se connecter</a>
            </div>

            <#else>
            
                <form class="" action="/form" method="post">
                    <div class="cust-input">
                        <p>Votre nom:</p>
                        
                            <@spring.formInput "useremail.name"/>

                    </div>   
                    <div class="error">   
                        <@spring.showErrors "<br>"/>
                    </div> 

                    <div class="cust-input">
                        <p>Votre email:</p>

                            <@spring.formInput "useremail.email"/>

                    </div>
                    <div class="error">
                        <@spring.showErrors "<br>"/>
                    </div>

                    <div class="cust-input">
                        <p> Votre message: </p>

                            <@spring.formTextarea "useremail.message"/>
                            
                    </div>
                    <div class="error">
                        <@spring.showErrors "<br>"/>
                    </div>
                    
                    
                    <div class="btn-row btn-submit">
                        <input class="gbtn" type="submit" value="Envoyer">
                        <a class="gbtn" href="<@spring.url '/login' />">Se connecter</a>
                    </div>
                    
                </form>
            </#if>

        </div>
    </section>
</body>
</html>
