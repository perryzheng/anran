bothPlayersIn = (room) ->
  room.player1Name? && room.player1Name.length != 0 && room.player2Name? && room.player2Name.length != 0

onlyPlayer1In = (room) ->
  room.player1Name? && room.player1Name.length != 0 && (!room.player2Name? || room.player2Name.length == 0)

$ ->
  $("body").on "submit", ".makeMove", (e) ->
    e.preventDefault()
    form = $(e.target)
    url = form.attr("action")
    $.ajax url,
      type: "POST"
      context: this
      data:
        pileIndex: $("input[name=pileIndex]", form).val()
        numBeans: $("input[name=numBeans]", form).val()
      success: (res) ->
        whichPlayer = res.whichPlayer
        pileList = res.piles.split(",")
        pileIndex = res.pileIndex
        actualTaken = res.actualTaken
        $("#history").append $("<li>").text "#{whichPlayer} took #{actualTaken} from pile #{pileIndex}"
        $(".piles").empty()
        $(".piles").append $("<span class='pile'>").text(pile) for pile in pileList
      error: (err) ->
        $(".moveError").text("Invalid move, please try again")

  $.get "/moves", (moves) ->
    $.each moves, (index, move) ->
      $("#history").append $("<li>").text "Took #{move.amountTaken} from pile #{move.pileIndex}"

  waiting = false
  playing = false
  numGetRoom = 0

  do getRoom = ->
    $.get "/getRoom", (room) ->
      if (bothPlayersIn(room))
        if (!playing)
          playing = true
          waiting = false
          $("form.joinForm").hide()
          $("#status").html $("<p>").text "#{room.player1Name} is playing against #{room.player2Name}"
      else if (onlyPlayer1In(room))
        if (!waiting)
          waiting = true
          playing = false
          $("form.joinForm").hide()
          $("#status").html $("<p>").text "Welcome, #{room.player1Name}! Waiting for your opponent.."
          $(".gameArea").show()
      setTimeout getRoom, 3000 unless numGetRoom > 2000