<link rel="stylesheet" href="${resource}/nivo-slider/themes/default/default.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${resource}/nivo-slider/nivo-slider.css" type="text/css" media="screen" />
<div class="slider-wrapper theme-default">
    <div id="widget-${widget.id}-slider" class="nivoSlider">
        ${widget.settings.content!!}
    </div>
</div>
<script type="text/javascript" src="${resource}/nivo-slider/jquery.nivo.slider.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        $('#widget-${widget.id}-slider').nivoSlider(${widget.settings.options!!});
    });
</script>