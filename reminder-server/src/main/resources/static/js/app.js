
var DateTime = luxon.DateTime;

$(function() {

    $('#date').flatpickr({

        enableTime: true,
        dateFormat: "Y-m-d H:i"
    });

    $('#reminder-form').submit(function( event ) {

        event.preventDefault();

        var create  = $('#create').button('creating');

        var $form   = $(this);
        var email   = $form.find( "input[name='email']" ).val();
        var message = $form.find( "textarea[name='message']" ).val();
        var oneOff  = $form.find( "input[name='one-off']" ).is(":checked");
        var date    = $form.find( "input[name='date']" ).val();

        $.ajax({

            beforeSend: function(xhrObj) {

                xhrObj.setRequestHeader('Content-Type', 'application/json');
                xhrObj.setRequestHeader('Accept', 'application/json');
            },
            url: '/api/reminder',
            data: JSON.stringify({

                email: email,
                message: message,
                oneOff: oneOff,
                dateTime: DateTime.fromFormat(date, 'yyyy-MM-dd HH:mm').toUTC()
            }),
            type: 'POST',
            dataType: 'json',
            success: function(data) {

                create.button('reset');
                $('#form-container').replaceWith('<h2>Your reminder has been created.</h2>');
            }
        });
    });
});
