import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import { useHistory, useLocation, Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { getAccessToken } from "../../../helpers/tokenControl";
import FormContainer from "./FormContainer";
import ProfileOrderListContainer from "./ProfileOrderListContainer";
import { RootState } from "../../../app/store";
import ProfileAddShop from "./ProfileAddShop";
import { updateManagerShop } from "../../../features/editFeatures/updateManagerShops";

const SHOP_BASE_URL = "http://54.180.134.20/api/manager/shops";

type theme = {
	checked: boolean;
};

type shopsDataType = {
	id: number;
	image: string;
	name: string;
	category: Array<string>;
	phone: string;
	address: string;
	hours: { close: string; open: string };
};

const ProfileContent: React.FC = () => {
	const [showProfileEdit, setShowProfileEdit] = useState<boolean>(true);
	const [showOrderList, setShowOrderList] = useState<boolean>(false);
	const [showManager, setShowManager] = useState<boolean>(false);
	// eslint-disable-next-line @typescript-eslint/no-unused-vars
	const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);

	const logChecker = useSelector((state: RootState) => {
		return state.user.email;
	});

	const location = useLocation();
	const history = useHistory();
	useEffect(() => {
		// Checking wheter getAccessToken is valid, if not, false will be returned.
		// Must be updated, getAccessToken is not created for this usage.
		if (logChecker) {
			// 이전에는 logchekcer.length 였음
			setIsLoggedIn(true);
		} else {
			setIsLoggedIn(false);
		}
	}, [logChecker, location, history]);

	const { user } = useSelector((state: RootState) => ({
		user: state.user.role,
	}));

	function handleShowProfileEdit(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowProfileEdit(true);
		setShowOrderList(false);
		setShowManager(false);
	}

	function handleShowOrderList(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowOrderList(true);
		setShowProfileEdit(false);
		setShowManager(false);
	}

	function handleShowManager(
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) {
		event.preventDefault();
		setShowProfileEdit(false);
		setShowOrderList(false);
		setShowManager(true);
	}

	if (!logChecker) {
		// eslint-disable-next-line no-alert
		alert("로그인해얌");
		return <>{history.push("/")}</>;
	}

	const BoardService = () => {
		// 상품 조회
		return axios.get(`${SHOP_BASE_URL}`, {
			headers: { Authorization: `Bearer ${getAccessToken()}` },
		});
	};
	const [manageShop, setManageShop] = useState<shopsDataType[]>([]);
	const dispatch = useDispatch();
	const displayShops = useSelector((state: RootState) => {
		return state.updateManagerShops;
	});
	useEffect(() => {
		BoardService().then(
			(res) => {
				setManageShop(res.data.data);
				dispatch(updateManagerShop(res.data.data));
			},
			() => {
				console.log("api 연결 안됨"); // api가 연결되지 않은 경우 -> 위의 예시 데이터 출력
			}
		);
	}, [displayShops]);

	return (
		<Content>
			<SelectionContainer>
				<div className="button-ctn">
					<ProfileSelectButton
						type="button"
						onClick={(e) => handleShowProfileEdit(e)}
						checked={showProfileEdit}
					>
						개인정보 수정
					</ProfileSelectButton>
					<ProfileSelectButton
						type="button"
						onClick={(e) => handleShowOrderList(e)}
						checked={showOrderList}
					>
						주문 내역
					</ProfileSelectButton>
					{user === "ROLE_MANAGER" ? (
						<>
							<ProfileSelectButton
								type="button"
								onClick={(e) => {
									handleShowManager(e);
								}}
								checked={showManager}
							>
								매점 관리
							</ProfileSelectButton>

							{showManager &&
								manageShop.map((row) => (
									<DropContent key={row.id}>
										<Link
											to={`manager/${row.id}`}
											id="managerBtn"
										>
											<button type="button">
												{row.name}
											</button>
										</Link>
										<Link
											to={`shopedit/${row.id}`}
											id="editBtn"
										>
											수정
										</Link>
									</DropContent>
								))}
						</>
					) : null}
				</div>
			</SelectionContainer>
			<ProfileEditContainer>
				{showProfileEdit && <FormContainer />}
				{showManager && <ProfileAddShop />}
				{showOrderList && <ProfileOrderListContainer />}
			</ProfileEditContainer>
		</Content>
	);
};

export default ProfileContent;

const ProfileSelectButton = styled.button<theme>`
	/* border: 1px dashed green; */
	/* position: relative; */
	top: 200px;
	text-align: left;
	width: 100%;
	height: 50px;
	font-size: 20px;
	font-weight: 400;
	margin: 0px;
	padding-left: 20px;
	transition-duration: 0.2s;
	background-color: ${({ checked }) =>
		checked ? `rgba(49, 114, 84, 0.13)` : "white"};

	:hover {
		color: #6e6b6b;
	}
`;

const DropContent = styled.li`
	width: 100%;
	height: 40px;
	display: flex;
	align-items: center;
	justify-content: flex-start;
	background-color: #f3f3f3;

	#editBtn {
		margin-left: 20px;
		font-size: 15px;
		color: #000;
	}
	#managerBtn {
		margin-left: 40px;
	}
	button:hover {
		color: #6e6b6b;
	}
	button {
		font-size: 18px;
		height: 100%;
		text-align: left;
		width: 100%;
		font-weight: 400;
		padding: 0;
		transition-duration: 0.2s;
	}
`;

const ProfileEditContainer = styled.div`
	width: 100%;
	margin: 0;
	padding: 0;
	border-left: 1px solid black;
	display: flex;
	flex-direction: column;
	min-height: 650px;
	max-height: auto;
`;

const Content = styled.div`
	margin: 0;
	padding: 0;
	border-left: 1px solid black;
	border-right: 1px solid black;
	border-bottom: 1px solid black;
	max-height: auto;
	display: flex;
	flex-direction: row;
	width: 907px;
	min-height: 660px;
	background: #ffffff;
	margin-bottom: 80px;
`;

const SelectionContainer = styled.div`
	margin: 0;
	padding: 0;
	display: flex;
	flex-direction: column;
	width: 229px;
	min-height: 650px;
	max-height: auto;
	background: #ffffff;

	.button-ctn {
		position: relative;
		top: 40px;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
`;
