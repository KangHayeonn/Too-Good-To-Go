import React, { useEffect, useState, useCallback, useRef } from "react";
import { useHistory } from "react-router-dom";
import styled from "@emotion/styled";
import CloseIcon from "@mui/icons-material/Close";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../app/store";
// rtk
import {
	incrementSelectedItems,
	decrementSelectedItems,
	deleteSelectedItem,
} from "../../../features/shopFeatures/selectMenuItemsSlice";
import { addItemsToCart } from "../../../features/cartFeatures/selectCartCardsSlice";
import Modal from "../../atoms/Modal/MoveToCartModal";

type buttonType = {
	children: React.ReactNode;
};

const CartContainer: React.FC<buttonType> = ({ children }) => {
	const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
	const dispatch = useDispatch();
	const history = useHistory();

	// login check
	const logChecker = useSelector((state: RootState) => {
		return state.user.email;
	});

	useEffect(() => {
		if (logChecker) {
			setIsLoggedIn(true);
		} else {
			setIsLoggedIn(false);
		}
	}, []);

	// modal 
	const [openModal, setOpenModal] = useState(false);
	const popRef = useRef<HTMLDivElement>(null);

	const onClickOutside = useCallback(
		({ target }) => {
			if (popRef.current && !popRef.current.contains(target)) {
				setOpenModal(false);
			}
		},
		[setOpenModal]
	);
	useEffect(() => {
		document.addEventListener("click", onClickOutside);
		return () => {
			document.removeEventListener("click", onClickOutside);
		};
	}, []);
	const onClickToggleModal = useCallback(() => {
		setOpenModal((prev) => !prev);
	}, [setOpenModal]);

	const showModal = useCallback(() => {
		return <Modal setModalOpen={setOpenModal} />;
	}, []);

	const isCheckedArr = useSelector((state: RootState) => {
		return state.selectMenuItems.filter((e) => {
			return e.isChecked;
		});
	});

	// Accumulate money for display
	const accumulatedAmount: number = isCheckedArr.reduce((accu, curr) => {
		// eslint-disable-next-line no-param-reassign
		accu += curr.discountedPrice * curr.cartItemQuantity;
		return accu;
	}, 0);

	const numberWithCommas = ((x: number) => {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	})(accumulatedAmount);

	return (
		<Wrapper>
			<div className="first-section">
				<p hidden>1</p>
			</div>
			<div className="second-section">
				<div className="menu">
					{isCheckedArr.map((e) => {
						return (
							<div className="menu-item" key={e.id}>
								<Item>
									<li key={e.id}>
										{e.name}
										{e.cartItemQuantity > 1 &&
											` x ${e.cartItemQuantity}`}
									</li>
									<button
										className="delete"
										type="button"
										onClick={() => {
											dispatch(
												deleteSelectedItem(e.id)
											);
										}}
									>
										<CloseIcon className="delete-icon" />
									</button>
								</Item>
								<QuantityButton>
									<button
										className="menu-item-button"
										type="button"
										onClick={() => {
											dispatch(
												decrementSelectedItems(e.id)
											);
										}}
									>
										-
									</button>
									<div>{e.cartItemQuantity}</div>
									<button
										className="menu-item-button"
										type="button"
										onClick={() => {
											dispatch(
												incrementSelectedItems(e.id)
											);
										}}
									>
										+
									</button>
								</QuantityButton>
							</div>
						);
					})}
				</div>
			</div>
			<div className="third-section">
				<p>총 {numberWithCommas}원</p>
			</div>
			<div className="fourth-section">
				<button
					type="button"
					onClick={() => {
						if (isLoggedIn) {
							dispatch(addItemsToCart(isCheckedArr));
							onClickToggleModal();
						} else {
							// eslint-disable-next-line no-alert
							alert("로그인이 필요합니다.");
							history.push("/login");
						}
					}}
				>
					{children}
				</button>
			</div>
			{!!openModal && showModal()}
		</Wrapper>
	);
};

export default CartContainer;

const Wrapper = styled.div`
	width: 330px;
	min-height: 291px;
	height: auto;
	border: 1px solid lightgrey;
	display: flex;
	flex-direction: column;
	justify-content: space-between;

	.first-section {
		height: 58px;
		background-color: #54b689;
	}

	.second-section {
		min-height: 99px;
		height: auto;
		border-bottom: 2px solid grey;
		display: flex;
		justify-content: center;
		align-items: flex-start;
		flex-direction: column;
		.menu {
			font-size: 20px;
			font-weight: bold;
		}
		.menu-item {
			border-bottom: 1px solid #eee;
			margin-bottom: 10px;
			margin-left: 10px;
			margin-top: 10px;
			width: 310px;

			li {
				margin: 10px;
				list-style: none;
				text-align: left;
			}
		}
	}
	.third-section {
		height: 47px;
		border-bottom: 2px solid grey;
		color: #54b689;
		font-weight: 700;
		font-size: 20px;
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}
	p {
		margin-right: 15px;
	}
	.fourth-section {
		height: 81px;
	}

	button {
		width: 152px;
		height: 45px;
		background: #54b689;
		color: white;
		font-size: 15px;
		font-weight: 700;
		border-radius: 12px;
		margin-top: 18px;
	}

	h1 {
		display: hidden;
	}
`;

const Item = styled.div`
	display: flex;
	justify-content: space-between;

	.delete {
		background: white;
		color: black;
		width: 33px;
		height: 19px;
		margin-top: 6px;
	}
	.delete-icon {
		font-size: 19px;
	}
`;

const QuantityButton = styled.div`
	width: 80px;
	height: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-direction: row;
	overflow: hidden;
	border-radius: 13px;
	background-color: #e7e4e4;
	margin: 10px 0 13px 220px;
	font-size: 14px;

	.menu-item-button {
		font-size: 17px;
		width: 30px;
		background: #e7e4e4;
		padding-bottom: 21px;
		color: #959595;
	}
`;
