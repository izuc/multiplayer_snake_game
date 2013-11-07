function UpdateUsers() {
        var url = "OnlineUsers.ajax";
	new Ajax.PeriodicalUpdater("online_users", url, {
		method: 'POST',
		frequency: 1
        });
}

function GetBattleRequests() {
        var url = "GetBattleRequests.ajax";
        var params = "type=battle";
	new Ajax.PeriodicalUpdater("battlerequests", url, {
		method: 'POST',
                parameters: params,
		frequency: 1
        });
}

function GetSentBattleRequests() {
        var url = "GetBattleRequests.ajax";
        var params = "type=sent";
	new Ajax.PeriodicalUpdater("sentrequests", url, {
		method: 'POST',
                parameters: params,
		frequency: 1
        });
}

function LoadBattle(text) {
        if (text.length > 0) {
            window.location = "battle.jsp?"+text
        }
}

function BattleLoadCheck() {
        var url = "BattleLoadCheck.ajax";
        new Ajax.PeriodicalUpdater("battle_load", url, {
            method: 'POST',
            frequency: 1,
            onSuccess:  function(response) {
                LoadBattle(response.responseText);
            }
        }); 
}
        
function CheckForm(form) {
        var valid = new Validation(form, {onSubmit: false, useTitles: true});
        return valid.validate();
}

function RequestBattle() {
        var url = window.location.search;
        var url_object = url.toQueryParams();
        var params = "user="+url_object.user;
        new Ajax.Updater("info_message", "RequestBattle.ajax", {
                method: 'post',
                parameters: params
        });
        $('info_message').style.display = '';
}
function ProcessAvatar() {
    	var button = $('uploadImage'), interval;
	new AjaxUpload("#uploadImage", {
		action: 'UploadAvatar.ajax',
		onSubmit: function(file, ext){		
			button.update('Uploading');
			this.disable();
			interval = window.setInterval(function(){
				var text = button.innerHTML;
				if (text.length < 13){
                                    button.update(text + '.');					
				} else {
                                    button.update('Uploading');				
				}
			}, 200);			
		},
		onComplete: function(file, response){
			button.update('Upload');
                        $('profile_image').src = response;
			window.clearInterval(interval);
			this.enable();						
		}
	});
}

function UpdateDetails() {
        if (CheckForm('UserUpdateForm')) {
            var params = "emailAddress="+$('emailAddress').value+"&message="+$('message').value;                  
            new Ajax.Updater("info_message", "UpdateUser.ajax", {
                method: 'post',
                parameters: params
            });
            $('info_message').style.display = '';
        }
}

function RegisterForm() {
    if (CheckForm('registerForm')) {
        var params = "userName="+$('userName').value+"&password="+$('password').value+
            "&emailAddress="+$('emailAddress').value+"&answer="+$('verification').value;                  
        new Ajax.Updater("info_message", "AddUser.ajax", {
            method: 'post',
            parameters: params
        });
        $('info_message').style.display = '';
    }
}

function LoginAction() {
     var form = $('UserLoginForm');
     if (CheckForm(form)) {
        form.submit();
     }
}

Event.observe(window, 'load', function() {
     UpdateUsers();
});

Event.observe('loginAction', 'click', function() {
     LoginAction();
});