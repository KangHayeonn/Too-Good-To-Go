/* eslint-disable react/jsx-props-no-spreading */
import React, { useState, useEffect } from "react";
import styled from "@emotion/styled";
import { useHistory } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../app/store";
import { tempSetPhone } from "../../../features/order/orderInfoSlice";

const Bar = styled.div`
	padding: 3em;
`;

const CustomerName = styled.div`
	display: flex;
	padding-top: 10px;
`;

const Text = styled.div`
	width: 13%;
	padding: 5px;
	padding-left: 29px;
	font-size: 15px;
	font-weight: 600;
	color: #646464;
	display: flex;
`;

const Input = styled.input`
	width: 23%;
	height: 27px;
	padding-left: 15px;
	margin-bottom: 15px;
	border: solid 1px #c4c4c4;

	&:focus {
		outline: 1px solid #dfdfdf;
	}
`;

const Sign = styled.h4`
	margin: 5px 1em 1em;
`;

type User = {
	first?: string | undefined;
	second?: string | undefined;
	third?: string | undefined;
};

const OrderInfo: React.FC = () => {
	const [phoneNum, setPhoneNum] = useState<string[]>();
	const [state, setState] = useState<User>();
	const dispatch = useDispatch();
	const logChecker = useSelector((state: RootState) => {
		return state.user.phone;
	});
	const orderInfoCheck = useSelector((state: RootState) => {
		return state.orderInfo.orderInfoCheck;
	});
	const history = useHistory();

	useEffect(() => {
		if (logChecker) {
			/* // 010-1111-2222 형식일 경우
			dispatch(tempSetPhone(logChecker));
			const phone = logChecker.split("-");
			setPhoneNum(phone);
			*/
			// 01011112222 형식일 경우
			const phone = new Array<string>();
			phone[0] = logChecker.substring(0,3);
			phone[1] = logChecker.substring(3,7);
			phone[2] = logChecker.substring(7,11);
			setPhoneNum(phone);
			dispatch(tempSetPhone(logChecker));
		} else {
			// eslint-disable-next-line no-alert
			alert("먼저 로그인이 필요합니다.");
			history.push("/login");
		}
	}, []);

	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		setState({
			...state,
			// Dynamic state changing.
			[event.target.name]: event.target.value,
		});
	};

	useEffect(() => {
		if (state)
			dispatch(
				// tempSetPhone(`${state.first}-${state.second}-${state.third}`)
				tempSetPhone(`${state.first}${state.second}${state.third}`)
			);
	}, [state]);

	return (
		<div>
			{orderInfoCheck === "USER" && phoneNum ? (
				<CustomerName>
					<Text>휴대전화 *</Text>
					<Input
						placeholder={phoneNum[0]}
						type="text"
						className="phone"
						disabled
					/>
					<Sign> - </Sign>
					<Input placeholder={phoneNum[1]} type="text" disabled />
					<Sign> - </Sign>
					<Input placeholder={phoneNum[2]} type="text" disabled />
				</CustomerName>
			) : (
				<CustomerName>
					<Text>휴대전화 *</Text>
					<Input
						placeholder=""
						type="text"
						name="first"
						onChange={handleChange}
					/>
					<Sign> - </Sign>
					<Input
						placeholder=""
						type="text"
						name="second"
						onChange={handleChange}
					/>
					<Sign> - </Sign>
					<Input
						placeholder=""
						type="text"
						name="third"
						onChange={handleChange}
					/>
				</CustomerName>
			)}
			<Bar />
		</div>
	);
};
export default OrderInfo;
