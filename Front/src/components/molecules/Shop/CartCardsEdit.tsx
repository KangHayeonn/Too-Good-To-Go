import React, { useEffect, useState } from "react";
// Styles
import styled from "@emotion/styled";
// Axios
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
// images
import fighting from "../../../../public/image/화이팅도치.jpg";
import CartCardEdit from "./CartCardEdit";
import { RootState } from "../../../app/store";
import { updateMenuItems } from "../../../features/shopFeatures/updateMenuItemsSlice";

interface shopMatchId {
	shopMatchId: string;
}

type productsDataType = {
	id: number;
	name: string;
	image: string;
	discountedPrice: number;
	price: number;
	shopId: number;
	shopName: string;
};

const BASE_URL = "http://54.180.134.20/api/shops";

const CartCardsEdit: React.FC<shopMatchId> = ({ shopMatchId }) => {
	const [products, setProducts] = useState<productsDataType[]>([]);
	const UpdateProduct = () => {
		return axios.get(`${BASE_URL}/${shopMatchId}/products/all`);
	};
	const dispatch = useDispatch();
	const update = () => {
		UpdateProduct().then(
			(res) => {
				setProducts(res.data.data);
				dispatch(updateMenuItems(res.data.data));
			},
			() => {
				console.log("product update fail");
			}
		);
	};
	const displayMenu = useSelector((state: RootState) => {
		return state.updateMenuItems;
	});

	useEffect(() => {
		update();
	}, []);

	if (displayMenu.length) {
		return (
			<Wrapper>
				{products.map((card) => {
					return (
						<CartCardEdit
							key={card.id}
							id={card.id}
							shopFoodImg={card.image}
							shopName={card.shopName}
							shopFoodName={card.name}
							shopBeforeCost={card.price}
							shopFoodCost={card.discountedPrice}
							shopMatchId={shopMatchId}
						/>
					);
				})}
			</Wrapper>
		);
	}
	return (
		<Wrapper>
			<img src={fighting} alt="화이팅도치의 존재" />
		</Wrapper>
	);
};
export default CartCardsEdit;

const Wrapper = styled.div`
	width: 100%;
	height: 579px;
	/* border: 1px solid blue; */
	border-top: 1px solid #d3d3d3;
	overflow: visible;
	overflow-x: hidden;
	padding: 30px 10px;
	margin-right: 25px;
	::-webkit-scrollbar {
		width: 10px;
	}
	::-webkit-scrollbar-thumb {
		background-color: #e7e4e4;
		border-radius: 12px;
		background-clip: padding-box;
		border: 2px solid transparent;
	}
	::-webkit-scrollbar-track {
		background-color: white;
		border-radius: 10px;
		box-shadow: inset 0px 0px 5px white;
	}
`;
