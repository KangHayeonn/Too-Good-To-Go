<<<<<<< HEAD
<<<<<<< HEAD
import React, {useState, useCallback, useRef, useEffect} from "react";
import styled from "@emotion/styled";
import Modal from '../../atoms/Modal/PaymentModal';

=======
import React, { useState, useCallback, useRef, useEffect } from "react";
import styled from "@emotion/styled";
import Modal from "../../atoms/Modal/PaymentModal";
>>>>>>> 42d13392b578288300887e87dec5e62484cde53e
=======
import React from "react";
import styled from "@emotion/styled";
import Dropdown from "../../atoms/DropDown/Dropdown";
>>>>>>> 66b08e25f1233dbe2f3e1b1edab7c4d2469d8694

const RequestShop = styled.div`
	display: flex;
	flex-direction: column;
	padding: 1em;
	margin-top: 0.5em;
	margin-bottom: 5.5em;
`;

const Text = styled.div`
	width: 13%;
	padding: 5px;
	padding-left: 21px;
	font-size: 16px;
	font-weight: 600;
	color: #646464;
	display: flex;
	margin-top: 0.5em;
`;

const Button = styled.div`
	width: 200px;
	height: 31px;
	border: none;
	display: flex;
	margin-top: 0.5em;
	margin-left: 1.5em;
	margin-bottom: 2em;
`;

const Input1 = styled.input`
	position: relative;
	vertical-align: middle;
	width: 1.27em;
	height: 1.27em;
	cursor: pointer;
	outline: none !important;
	border: 2px solid #cfcfcf;
	background: #ffffff;
	border-radius: 3px;
`;

const Label = styled.label`
	margin-left: 0.7em;
	font-size: 14px;
	font-weight: 600;
	color: #525252;
`;
const PaymentInfo: React.FC = () => {
<<<<<<< HEAD
	const [show, setShow] = useState(false);
	const popRef = useRef<HTMLDivElement>(null);

	const onClickOutside = useCallback(
<<<<<<< HEAD
		({target}) => {
			if(popRef.current && !popRef.current.contains(target)) {
=======
		({ target }) => {
			if (popRef.current && !popRef.current.contains(target)) {
>>>>>>> 42d13392b578288300887e87dec5e62484cde53e
				setShow(false);
			}
		},
		[setShow]
	);
	useEffect(() => {
<<<<<<< HEAD
		document.addEventListener('click', onClickOutside);
		return() => {
			document.removeEventListener('click', onClickOutside);
		};
	}, []);
	const onClickToggleModal = useCallback(() => {
		setShow(prev => !prev);
=======
		document.addEventListener("click", onClickOutside);
		return () => {
			document.removeEventListener("click", onClickOutside);
		};
	}, []);
	const onClickToggleModal = useCallback(() => {
		setShow((prev) => !prev);
>>>>>>> 42d13392b578288300887e87dec5e62484cde53e
	}, [setShow]);
=======
>>>>>>> 66b08e25f1233dbe2f3e1b1edab7c4d2469d8694
	return (
		<>
			<RequestShop>
				<Text>결제수단 선택</Text>
				<Dropdown>결제 수단을 선택해주세요.</Dropdown>
				<Button>
					<Input1 type="checkbox" id="nextusepayment" />
					<Label htmlFor="nextusepayment">다음에도 사용</Label>
				</Button>
<<<<<<< HEAD
				<div ref={popRef}>
<<<<<<< HEAD
					<button type="button" onClick={onClickToggleModal}>버튼</button>
=======
					<button type="button" onClick={onClickToggleModal}>
						버튼
					</button>
>>>>>>> 42d13392b578288300887e87dec5e62484cde53e
					<Modal show={show} />
				</div>
=======
>>>>>>> 66b08e25f1233dbe2f3e1b1edab7c4d2469d8694
			</RequestShop>
		</>
	);
};

export default PaymentInfo;
