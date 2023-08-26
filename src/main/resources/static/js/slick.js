// footer 공지사항 스와이퍼
const infoSwiper = {
	init() {
		let $infoSwiper = document.querySelector(".info-swiper");

		if ($infoSwiper) {
			var swiper = new Swiper(".info-swiper", {
				loop: true,
				/* autoplay: {
					delay: 3000,
				}, */
				slidesPerView: 1,
				spaceBetween: 10,
				direction: "vertical",
				navigation: {
					nextEl: ".notice-next",
					prevEl: ".notice-prev",
				},
				speed: 900,
				autoplay: {
					delay: 2000,
				},
			});
		}
	},
};
