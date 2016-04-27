describe("CKEditor", function() {

    beforeEach(function(){
        beforeInitCkPlugin();
        initCkPlugin = loadCkPlugin();
    });

    it("should has CKEDITOR", function(){
        expect(CKEDITOR).toBeDefined();
    })

});
