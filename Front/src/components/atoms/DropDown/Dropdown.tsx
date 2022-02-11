import React, { useState, useCallback } from "react";
import styled from "@emotion/styled";
import { useDispatch } from "react-redux";
import { setPaymentMethod } from "../../../features/order/orderInfoSlice";

const dropdownItems = [
	{ id: 1, name: "무통장 입금" },
	{ id: 2, name: "신용/체크카드" },
	{ id: 3, name: "핸드폰 결제" },
	{ id: 4, name: "카카오페이" },
	{ id: 5, name: "직접 만나서 결제" },
];

const DropdownContainer = styled.div`
	width: 403px;
	heigth: 30px;
	z-index: 1;
	margin: 0.7em 0 1em 1.3em;
	&:hover {
		cursor: pointer;
	}
`;
const DropdownBody = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 9px 14px;
	border: 2px solid #d2d2d2;
`;

const DropdownSelect = styled.p`
	height: 13px;
	color: #646464;
	font-size: 13px;
`;

const DropdownMenu = styled.ul<{ isActive: boolean }>`
	display: ${(props) => (props.isActive ? "block" : "none")};
	width: 400px;
	background-color: white;
	position: absolute;
	border: 1px solid #cfcfcf;
`;

const DropdownItemContainer = styled.li`
	display: flex;
	justify-content: space-between;
	align-items: center;
	color: #646464;

	padding: 9px 14px;
	border-top: none;
	&:last-child {
		border-bottom: none;
	}
	&:hover {
		background: #d6e3dd;
	}
`;

const ItemName = styled.p`
	font-size: 13px;
	font-weight: 600;
`;

type Type = {
	children: React.ReactNode;
};

const DropDown: React.FC<Type> = ({ children }) => {
	const [isActive, setIsActive] = useState(false);
	const [item, setItem] = useState(null);
	const dispatch = useDispatch();

	const onActiveToggle = useCallback(() => {
		setIsActive((prev) => !prev);
	}, []);
	const onSelectItem = useCallback((e) => {
		const targetId = e.target.id;

		if (targetId === "item_name") {
			setItem(e.target.parentElement.innerText);
		} else if (targetId === "item") {
			setItem(e.target.innerText);
			dispatch(setPaymentMethod(e.target.innerText));
		}
		setIsActive((prev) => !prev);
	}, []);
	return (
		<DropdownContainer>
			<DropdownBody onClick={onActiveToggle}>
				{item ? (
					<>
						<ItemName>{item}</ItemName>
					</>
				) : (
					<>
						<DropdownSelect>{children}</DropdownSelect>
					</>
				)}
			</DropdownBody>
			<DropdownMenu isActive={isActive}>
				{dropdownItems.map((item) => (
					<DropdownItemContainer
						id="item"
						key={item.id}
						onClick={onSelectItem}
					>
						<ItemName id="item_name">{item.name}</ItemName>
					</DropdownItemContainer>
				))}
			</DropdownMenu>
		</DropdownContainer>
	);
};

export default DropDown;
