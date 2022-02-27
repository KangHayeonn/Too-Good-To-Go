import React, { useState, useEffect } from "react";
import axios from "axios";
import TitleTemplate from "../../templates/TitleTemplate";

type shopsDataType = {
	id: number;
	image: string;
	name: string;
	phone: string;
	address: string;
	category: Array<string>;
	hours: {
		open: string;
		close: string;
	};
};

const SHOP_BASE_URL = "http://54.180.134.20/api/shops";

interface shopMatchId {
	shopMatchId?: string;
	isEdit: boolean;
}
const ShopTitles: React.FC<shopMatchId> = ({ shopMatchId, isEdit }) => {
	const [shop, setShop] = useState<shopsDataType>();
	const BoardService = () => {
		// 가게 조회
		return axios.get(`${SHOP_BASE_URL}/${shopMatchId}`);
	};
	useEffect(() => {
		BoardService().then(
			(res) => {
				setShop(res.data.data); // api가 연결 된 경우 -> back에서 데이터 불러옴
			},
			() => {
				console.log("api 연결 실패 ");
			}
		);
	}, []);

	return (
		<div>
			{shop === undefined ? (
				<div>api 연결 실패</div>
			) : (
				<TitleTemplate
					key={shop.id}
					shopMatchId={shop.id.toString()}
					image={shop.image}
					title={shop.name}
					time={`${shop.hours.open} ~ ${shop.hours.close}`}
					address={shop.address}
					phone={shop.phone}
					isEdit={isEdit}
					category={shop.category}
					hours={shop.hours}
				/>
			)}
		</div>
	);
};

ShopTitles.defaultProps = {
	shopMatchId: "",
};

export default ShopTitles;
