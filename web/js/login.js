/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// This function is used for the submission of forms with two submit inputs.
//  It allows us to determine which submit was selected.
$(document).ready(function(){
    $(":submit").click(function(){
        $("#action").val(this.name);
    });
});



