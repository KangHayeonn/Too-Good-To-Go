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
import { CartCardType } from "../../../features/shopFeatures/updateMenuItemsSlice";
import { updatePriorities } from "../../../features/editFeatures/updatePriority";
import { getAccessToken } from "../../../helpers/tokenControl";

interface shopMatchId {
	shopMatchId: string;
}

const BASE_URL = "http://54.180.134.20/api/shops";
const BASE_URL_PUT = "http://54.180.134.20/api/manager/shops";

const CartCardsEdit: React.FC<shopMatchId> = ({ shopMatchId }) => {
	const displayMenu = useSelector((state: RootState) => {
		return state.updateMenuItems;
	});

	const [products, setProducts] = useState<CartCardType>(displayMenu);
	const [productsPriority, setProductsPriority] = useState<Array<string>>([]);
	const UpdateProduct = () => {
		return axios.get(`${BASE_URL}/${shopMatchId}/products/all`);
	};
	const UpdateProductPriority = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		};
		return axios.put(
			`${BASE_URL_PUT}/${shopMatchId}/products`,
			{
				productsId: productsPriority,
			},
			config
		);
	};
	const dispatch = useDispatch();
	const update = () => {
		UpdateProduct().then(
			(res) => {
				setProducts(res.data.data);
				console.log("success");
			},
			() => {
				console.log("product update fail");
			}
		);
	};
	const updatePriority = () => {
		UpdateProductPriority().then(
			() => {
				update();
				console.log("success");
			},
			() => {
				console.log("product update fail");
			}
		);
	};
	const updateProductChange = useSelector((state: RootState) => {
		return state.updateProductBooelan;
	});

	useEffect(() => {
		update();
	}, [updateProductChange]);

	useEffect(() => {
		update();
		return () => {
			dispatch(updatePriorities(false));
		};
	}, []);

	const reduxPriorityStatus = useSelector((state: RootState) => {
		return state.updatePriority;
	});

	const OnPriorityBtn = () => {
		// eslint-disable-next-line no-unused-expressions
		reduxPriorityStatus === true
			? dispatch(updatePriorities(false))
			: dispatch(updatePriorities(true));
	};

	const changePriorityDown = (arrNum: number) => {
		const newArr = products;
		if (arrNum !== newArr.length - 1) {
			const item = newArr.splice(arrNum, 1);
			newArr.splice(arrNum + 1, 0, item[0]);
			setProducts(newArr);
			const priorityNumber = newArr.map((item) => item.id.toString());
			console.log(priorityNumber);
			setProductsPriority(priorityNumber);
			updatePriority();
		}
	};
	const changePriorityUp = (arrNum: number) => {
		const newArr = products;
		if (arrNum !== 0) {
			const item = newArr.splice(arrNum, 1);
			newArr.splice(arrNum - 1, 0, item[0]);
			setProducts(newArr);
			const priorityNumber = newArr.map((item) => item.id.toString());
			setProductsPriority(priorityNumber);
			console.log(productsPriority);
			updatePriority();
		}
	};

	if (products.length) {
		return (
			<Wrapper btnStatus={reduxPriorityStatus}>
				<div className="btn-header">
					<button
						type="button"
						className="button-change"
						onClick={OnPriorityBtn}
					>
						우선순위 변경
					</button>
				</div>

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
							priorityUpFn={changePriorityUp}
							priorityDownFn={changePriorityDown}
							cardIndex={products.indexOf(card)}
						/>
					);
				})}
			</Wrapper>
		);
	}
	return (
		<Wrapper btnStatus={reduxPriorityStatus}>
			<img src={fighting} alt="화이팅도치의 존재" />
		</Wrapper>
	);
};
export default CartCardsEdit;

type theme = {
	btnStatus: boolean;
};

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
	.btn-header {
		display: flex;
	}
	.button-change {
		margin-left: 20px;
		width: 89px;
		height: 27px;
		border-radius: 8px;
		background-color: ${(props: theme) =>
			props.btnStatus === true ? "#469972" : "#55b689"};
		color: #fff;
		font-weight: 500;
		font-size: 13px;
		${(props: theme) =>
			props.btnStatus &&
			`box-shadow: 2px 2px 2px 1px rgba(84, 182, 137, 0.3)`}
	}
`;
