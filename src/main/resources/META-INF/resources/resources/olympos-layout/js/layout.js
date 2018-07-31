/** 
 * PrimeFaces Olympos Layout
 */
PrimeFaces.widget.Olympos = PrimeFaces.widget.BaseWidget.extend({
  
    init: function(cfg) {
        this._super(cfg);
        this.menuWrapper = $('#layout-menu-cover');
        this.menu = $('#layout-menu');
        this.menulinks = this.menu.find('a.menulink');
        this.menuResizeButton = $('#menu-resize-btn');
        this.menuPopupButton = $('#mobile-menu-btn');
        this.layoutTabmenu = $('#layout-tab-menu');
        this.layoutSubmenus = this.menuWrapper.find('.layout-tab-submenu');
        this.body = $('body');
        this.expandedMenuitems = this.expandedMenuitems||[];
        this.layoutMenuClick = false;

        this.restoreMenuState();
        this.bindEvents();
    },
    
    bindEvents: function() {
        var $this = this;
        
        this.equalizeTabMenuHeights();
        $(".nano").nanoScroller({flash: true});
        
        // open layout tab menu sub menus
        this.layoutTabmenu.find('a').on('click', function () {
            $this.layoutTabmenu.find('li a').removeClass('active');
            $(this).addClass('active');
            $this.layoutSubmenus.removeClass('active');
            $('#' + String($(this).attr('rel'))).addClass('active');

            $this.equalizeTabMenuHeights();
        });
        
        // change menu mode from expanded to slim
        this.menuResizeButton.on('click',function() {
            if($this.body.hasClass('SlimMenu')){
                $(this).removeClass('active');
                $this.body.removeClass('SlimMenu');
            }
            else{
                $(this).addClass('active');
                $this.body.addClass('SlimMenu');
            }
        });
       
        // show and hide popup main menu when click a menu button
        this.menuPopupButton.off('click.popupMenubutton').on('click.popupMenubutton', function(e){
            $this.layoutMenuClick = true;
            
            if($this.menuWrapper.hasClass('active')) {
                $(this).removeClass('active');
                $this.menuWrapper.removeClass('active');
            }
            else {
                $(this).addClass('active');
                $this.menuWrapper.addClass('active');
            }
            e.preventDefault();
        });
        
        
        this.menulinks.on('click',function(e) {
            var menuitemLink = $(this),
            menuitem = menuitemLink.parent(),
            parentSubmenu = menuitem.parent('ul').parent('li').closest('ul');

            if(parentSubmenu.attr('id') == "layout-menu") {
                $this.resetActiveMenuitems();
            }

            if(menuitem.hasClass('active-menu-parent')) {
                menuitem.removeClass('active-menu-parent');
                menuitemLink.removeClass('active-menu').next('ul').removeClass('active-menu');
                $this.removeMenuitem(menuitem.attr('id'));
            }
            else {
                var activeSibling = menuitem.siblings('.active-menu-parent');
                if(activeSibling.length) {
                    activeSibling.removeClass('active-menu-parent');
                    $this.removeMenuitem(activeSibling.attr('id'));

                    activeSibling.find('ul.active-menu,a.active-menu').removeClass('active-menu');
                    activeSibling.find('li.active-menu-parent').each(function() {
                        var menuitem = $(this);
                        menuitem.removeClass('active-menu-parent');
                        $this.removeMenuitem(menuitem.attr('id'));
                    });
                }

                menuitem.addClass('active-menu-parent');
                menuitemLink.addClass('active-menu').next('ul').addClass('active-menu');
                $this.addMenuitem(menuitem.attr('id'));
            }

            if(menuitemLink.next().is('ul')) {
                e.preventDefault();
            }
            
            $this.saveMenuState();
            
            $this.equalizeTabMenuHeights();
        });
        
        this.menuWrapper.on('click', function() {
            $this.layoutMenuClick = true;
        });
        
        this.clickNS = 'click.' + this.id;
        $(document.body).off(this.clickNS).on(this.clickNS, function (e) {
            var isPopup = ($this.body.hasClass('PopupMenu') || document.documentElement.clientWidth <= 960);
            if(!$this.layoutMenuClick && isPopup) {
                $this.menuPopupButton.removeClass('active');
                $this.menuWrapper.removeClass('active');
            }
            
            $this.layoutMenuClick = false;
        });
    },
    
    removeMenuitem: function(id) {        
        this.expandedMenuitems = $.grep(this.expandedMenuitems, function(value) {
            return value !== id;
        });
    },
    
    addMenuitem: function(id) {
        if($.inArray(id, this.expandedMenuitems) === -1) {
            this.expandedMenuitems.push(id);
        }
    },
    
    saveMenuState: function() {
        $.cookie('olympos_expandeditems', this.expandedMenuitems.join(','), {path:'/'});
    },
    
    clearMenuState: function() {
        $.removeCookie('olympos_expandeditems', {path:'/'});
    },
    
    restoreMenuState: function() {
        var menucookie = $.cookie('olympos_expandeditems');
        if(menucookie) {
            this.expandedMenuitems = menucookie.split(',');
            for(var i = 0; i < this.expandedMenuitems.length; i++) {
                var id = this.expandedMenuitems[i];
                if(id) {
                    var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g,"\\:"));
                    menuitem.addClass('active-menu-parent');
                    menuitem.children('a,ul').addClass('active-menu');
                }             
            }
        }
    },
    
    resetActiveMenuitems: function() {
        for(var i = 0; i < this.expandedMenuitems.length; i++) {
            var id = this.expandedMenuitems[i];
            if(id) {
                var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g,"\\:"));
                menuitem.removeClass('active-menu-parent');
                menuitem.children('a,ul').removeClass('active-menu');
            }             
        }
        this.expandedMenuitems = [];
    },
    
    equalizeTabMenuHeights: function() {
        var activeTabMenuMaxHeight = 0,
            $this = this;
    
        this.layoutSubmenus.height('auto');
        this.layoutTabmenu.height('auto');

        this.layoutSubmenus.each(function() {
            var submenu = $(this);
            if(submenu.hasClass('active') && submenu.height() > activeTabMenuMaxHeight){
                activeTabMenuMaxHeight = submenu.height();

                if($this.layoutTabmenu.height() < activeTabMenuMaxHeight){
                    $this.layoutTabmenu.height(activeTabMenuMaxHeight);
                }
            }
        });

        $(".nano").nanoScroller();
    },
    
    isMobile: function() {
        return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(window.navigator.userAgent));
    },
    
    changeBodyClass: function (changedClass) {
        var bodyClasses = this.body.attr('class');
        
        if(changedClass === "") {
            changedClass = bodyClasses.replace("SlimMenu", "").replace("PopupMenu", "");
        }
        else if(changedClass === "SlimMenu") {
            bodyClasses = bodyClasses.replace("PopupMenu", "");
            changedClass = bodyClasses + " " + changedClass;
        }
        else if(changedClass === "PopupMenu") {
            bodyClasses = bodyClasses.replace("SlimMenu", "");
            changedClass = bodyClasses + " " + changedClass;
        }
        else {
            if(bodyClasses.indexOf("SlimMenu") >= 0) {
                changedClass = changedClass + " SlimMenu";
            }
            else if(bodyClasses.indexOf("PopupMenu") >= 0) {
                changedClass = changedClass + " PopupMenu";
            }
        }

        this.body.removeClass().addClass(changedClass);
        this.menuPopupButton.removeClass('active');
        this.menuResizeButton.removeClass('active');
    }
});

/* Issue #924 is fixed for 5.3+ and 6.0. (compatibility with 5.3) */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Dialog) {
    PrimeFaces.widget.Dialog = PrimeFaces.widget.Dialog.extend({
        
        enableModality: function() {
            this._super();
            $(document.body).children(this.jqId + '_modal').addClass('ui-dialog-mask');
        },
        
        syncWindowResize: function() {}
    });
}