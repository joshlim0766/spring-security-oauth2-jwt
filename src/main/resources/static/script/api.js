'use string';

$('#signup').on('submit', (e) => {
    e.preventDefault();

    $.ajax({
        type: 'POST',
        url : '/api/v1/signup',
        data : {
            'client_id' : $('#client_id').val(),
            'user_name' : $('#signup_user_name').val(),
            'password' : $('#signup_password').val()
        }
    }).then((response) => {
        localStorage.setItem('access_token', response.access_token);
        localStorage.setItem('refresh_token', response.refresh_token);

        $('#client_id').val('');
        $('#signup_user_name').val('');
        $('#signup_password').val('');
        $('#api_test_form').show();
    });
});

$('#signin').on('submit', (e) => {
    e.preventDefault();

    $.ajax({
        type: 'POST',
        url : '/api/v1/signin',
        data : {
            'client_id' : $('#client_id').val(),
            'user_name' : $('#signin_user_name').val(),
            'password' : $('#signin_password').val()
        }
    }).then((response) => {
        /*
        localStorage.setItem('access_token', response.access_token);
        localStorage.setItem('refresh_token', response.refresh_token);
        */

        $('#client_id').val('');
        $('#signin_user_name').val('');
        $('#signin_password').val('');
        $('#signin_form').hide();

        $('#token_refresh_form').show();
        $('#api_test_form').show();
    });
});

$('#refresh_access_token').on('click', (e) => {

});

