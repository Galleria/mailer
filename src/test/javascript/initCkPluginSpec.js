describe("CKEDITOR", function() {

  beforeEach(function() {
    configCkPluginSize();
    var initCkPlugin = loadCkPlugin();
    //initCkPlugin();
  });

  it("should has CKEDITOR", function(){
    expect(CKEDITOR).toBeDefined();
  });

});