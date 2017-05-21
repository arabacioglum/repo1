/**
 * 
 */
function renderImage(file) {
  var reader = new FileReader();
  reader.onload = function(event) {
    the_url = event.target.result
    $('#some_container_div').html("<img src='" + the_url + "' />")
  }
  reader.readAsDataURL(file);
}

$("#the-file-input").change(function() {
    console.log(this.files)
    renderImage(this.files[0])
});