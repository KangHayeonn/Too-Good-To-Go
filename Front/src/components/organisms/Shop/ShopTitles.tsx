import React from "react";
import TitleTemplate from "../../templates/TitleTemplate";
// images
import fighting from "../../../../public/image/shoptitle.png";

const ShopCards: React.FC = () => {
	return (
		<TitleTemplate
			image={fighting}
			title="강여사 김치찜"
			time="영업시간 10:00 ~ 22:00"
		/>
	);
};

export default ShopCards;
