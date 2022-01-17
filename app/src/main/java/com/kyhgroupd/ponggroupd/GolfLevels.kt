package com.kyhgroupd.ponggroupd

/**
 * GolfLevel class contains an Array with breakout levels.
 * Each character represents a GameObject on screen.
 */
class GolfLevels {

    /*
    *  O = goal
    *  L = light brick
    *  M = medium brick
    *  S = strong brick
    *  U = unbreakable brick
    *  , = line splitter
    * */

    val levels = arrayOf(


        //Level 1
        "UU   O   UU,"+
        "U         U,"+
        "U         U,"+
        "     L     ,"+
        "     M     ,"+
        "    LSL    ,"+
        "    LSL    ,"+
        "    MSM    ,"+
        "   LSSSL   ,"+
        "   MSSSM   ,"+
        "  LSSSSSL  ,"+
        "  LSMSMSL  ,"+
        "  LSMSMSL  ,"+
        "  LMLSLML  ,"+
        "  LLLSLLL  ,"+
        "    LSL    ,"+
        "    LSL    ,"+
        "    LSL    ,"+
        "    LSL    ,"+
        "    LSL    ,"+
        "    LML    ,"+
        "    LML    ,"+
        "    LLL    ",

        //level 2
        "LLLLLLLLLLL,"+
        "LLLLLLLLLLL,"+
        "LLLMMMMMLLL,"+
        "LLLMSSSMLLL,"+
        "LLLMSOSMLLL,"+
        "LLLMS SMLLL,"+
        "LLLMSSSMLLL,"+
        "LLLMMMMMLLL,"+
        "LLLLLLLLLLL,"+
        "LLLLLLLLLLL",

        //level 3
        "SSSSSSSSSSS,"+
        "SML     LMS,"+
        "SL       LS,"+
        "S         S,"+
        "S  U O U  S,"+
        "S  U   U  S,"+
        "S  UUUUU  S,"+
        "S         S,"+
        "SMMMMMMMMMS",

        //level 4
        "S    O    S,"+
        "S         S,"+
        "SLLLLLLLLLS,"+
        "S         S,"+
        "SMMMMMMMMMS,"+
        "S         S,"+
        "UUUU   UUUU,"+
        "SSSSSSSSSSS",

        //Level 5
        "MSSSSSSSSSM,"+
        " MSSSSSSSM ,"+
        "  MSSSSSM  ,"+
        "   MSSSM   ,"+
        "    MSM    ,"+
        "M    M    M,"+
        "SM       MS,"+
        "SSM     MSS,"+
        "SSSM O MSSS,"+
        "SSSM   MSSS,"+
        "SSM     MSS,"+
        "SM       MS,"+
        "M    M    M,"+
        "    MSM    ,"+
        "   MSSSM   ,"+
        "  MSSSSSM  ,"+
        " MMUUUUUMM " ,

    )
}