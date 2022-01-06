import React from "react";
import { Swiper, SwiperSlide } from "swiper/react/swiper-react.js";
import SwiperCore, { Navigation, Pagination, Autoplay } from "swiper";
import { css } from "@emotion/react";
import "swiper/swiper-bundle.min.css";
import "swiper/swiper.min.css";
import MainSwiperContent from "./MainSwiperContent";

SwiperCore.use([Navigation, Pagination, Autoplay]);

const changeColor = css`
	padding: 80px 0 30px 0;
	.swiper-pagination-bullet-active {
		background: #54b689;
	}

	.swiper-button-prev,
	.swiper-button-next {
		color: #54b689;
	}
`;

const textStyle = css`
	font-size: 40px;
	color: #333;
	font-weight: 700;
	display: flex;
	align-items: center;
	left: 0;
	position: absolute;

	p {
		color: red;
		margin-left: 10px;
		font-size: 30px;
		font-weight: 700;
	}
`;

const MainSwiper: React.FC = () => {
	return (
		<>
			<h1 css={textStyle}>
				이거 어때?<p>Now</p>
			</h1>
			<Swiper
				css={changeColor}
				className="banner"
				spaceBetween={50}
				slidesPerView={1}
				navigation
				loop
				pagination={{ clickable: true }}
				autoplay={{ delay: 5000 }}
			>
				<SwiperSlide>
					<MainSwiperContent start={0} last={4} />
				</SwiperSlide>
				<SwiperSlide>
					<MainSwiperContent start={4} last={8} />
				</SwiperSlide>
				<SwiperSlide>
					<MainSwiperContent start={8} last={12} />
				</SwiperSlide>
			</Swiper>
		</>
	);
};

export default MainSwiper;
