SELECT *
FROM Monster INNER JOIN MonsterLevel
ON Monster.monstername = MonsterLevel.monstername
WHERE levelname = ?
