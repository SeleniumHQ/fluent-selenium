var KRAFTWorks = typeof(KRAFTWorks) === 'undefined' ? {} : KRAFTWorks;

KRAFTWorks.stiltonBuy = {
    bindWatermark : function(element, text) {
        element.watermark(text, {className: 'stiltonBuyLoginInputDefaultText'});
    },
};
