$ ->
  $.get "/moves", (moves) ->
    $.each moves, (index, move) ->
      $("#history").append $("<li>").text "Took #{move.amountTaken} from pile #{move.pileIndex + 1}"