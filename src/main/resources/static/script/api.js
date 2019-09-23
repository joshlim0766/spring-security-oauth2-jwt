'use string';

$('#signup').on('submit', (e) => {
    e.preventDefault();

    $.ajax({
        type: 'POST',
        url : '/api/v1/signup',
        data : {
            'client_id' : $('#signup_client_id').val(),
            'user_name' : $('#signup_user_name').val(),
            'password' : $('#signup_password').val()
        }
    }).then((response) => {
        localStorage.setItem('access_token', response.access_token);
        localStorage.setItem('refresh_token', response.refresh_token);
        window.location.href = '/';
    });
});

