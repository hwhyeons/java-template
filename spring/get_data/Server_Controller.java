    @RequestMapping(value = "/getWorkByDate",method=GET)
    @ResponseBody
    public String getWorkByDate(@RequestParam String date) {
        // 리턴 타입을 String이 아니라 Work와 같이 객체로 변환 돼서 리턴 됨
        // List<Work> allByDate = workRepository.findAllByDate(date);
        return "tmp_test_return_value";
    }
