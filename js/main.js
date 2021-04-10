
var $messages = $('.messages-content'),
d, h, m,
i = 0;


// Handle Opening remarks
var opening_remarks = [
  'Welcome!',
  'Stay updated with latest corona virus cases in India..',
  'To get started you could enter the state/district/city name to get the latest updates with case history',
  'or Type "Top 10 states" to get Top 10 affected States',
  'or Type "Top 10 districts of <State name>(No spaces)" to get Top 10 affected districts',
  'Finally you can also type "Show states/districts/cities" to get the complete list of affected regions'
]
var ni = 0, howManyTimes = opening_remarks.length;

$(window).load(function() {
  $messages.mCustomScrollbar();
      fakeMessage();
});


function fakeMessage() {
  $('<div class="message loading new"><figure class="avatar"><img src="static/img/help.png" /></figure><span></span></div>').appendTo($('.mCSB_container'));
  updateScrollbar();

  function f() {
    $('.message.loading').remove();
    $('<div class="message new"><figure class="avatar"><img src="static/img/help.png" /></figure>' + opening_remarks[ni] + '</div>').appendTo($('.mCSB_container')).addClass('new');
    setDate();
    updateScrollbar();
    ni++;
      if( ni < howManyTimes ){
          setTimeout( f, 2000 );
      }
  }
  
  f();

}


// Handle Chat response here(GET):
function getBotResponse(rawText) {

    $('<div class="message loading new"><figure class="avatar"><img src="static/img/help.png" /></figure><span></span></div>').appendTo($('.mCSB_container'));
    updateScrollbar();

    // document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});

    $.get("/get", { msg: rawText }).done(function(data) {
      $('.message.loading').remove();
      if (data=='reload'){

        data_new = "Sorry Invalid selection. Please try again"
        $('<div class="message new"><figure class="avatar"><img src="static/img/help.png" /></figure>' + data_new + '</div>').appendTo($('.mCSB_container')).addClass('new');
        setDate();
        updateScrollbar();
        // document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});
        setTimeout(location.reload.bind(location), 1000);
      }
      else{

        var data_new = data.replace(/\n/g, "<br />");
        var auto_data = data.split("\n");
        $('<div class="message new"><figure class="avatar"><img src="static/img/help.png" /></figure>' + data_new + '</div>').appendTo($('.mCSB_container')).addClass('new');
        
        setDate();
        updateScrollbar();
        // document.getElementById('userInput').scrollIntoView({block: 'start', behavior: 'smooth'});

      }

      
      
    });
  }


// Listen for chat events here
$("#textInput").keypress(function(e) {
    if(e.which == 13) {
        insertMessage();
        
    }
});

$('.message-submit').click(function() {
  insertMessage();
});

$(window).on('keydown', function(e) {
  if (e.which == 13) {
      insertMessage();
      return false;
  }
})

$("#buttonInput").click(function() {
  insertMessage();
})
$("#clearInput").click(function() {
  location.reload();
})



function insertMessage() {
  msg = $('.message-input').val();
  if ($.trim(msg) == '') {
      return false;
  }
  $('<div class="message message-personal">' + msg + '</div>').appendTo($('.mCSB_container')).addClass('new');
  setDate();
  $('.message-input').val(null);
  updateScrollbar();
  getBotResponse(msg);
}


function updateScrollbar() {
  $messages.mCustomScrollbar("update").mCustomScrollbar('scrollTo', 'bottom', {
      scrollInertia: 10,
      timeout: 0
  });
}

function setDate() {
  d = new Date()
  if (m != d.getMinutes()) {
      m = d.getMinutes();
      $('<div class="timestamp">' + d.getHours() + ':' + m + '</div>').appendTo($('.message:last'));
  }
}
